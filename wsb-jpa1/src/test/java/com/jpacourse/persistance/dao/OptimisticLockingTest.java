package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing optimistic locking mechanism.
 * Uses existing entities from data.sql instead of creating new ones to avoid ID conflicts.
 */
@SpringBootTest
public class OptimisticLockingTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    
    // ID of an existing patient from data.sql
    private static final Long EXISTING_PATIENT_ID = 3L;
    
    private PatientEntity testPatient;
    private Long initialVersion;
    
    @BeforeEach
    public void setUp() {
        // Initialize version field for all patients if needed
        initializeVersionField();
        
        // Load the test patient
        testPatient = loadExistingPatient();
        initialVersion = testPatient.getVersion();
        
        // Ensure version is not null
        assertNotNull(initialVersion, "Version field must not be null");
    }

    /**
     * Initialize version field for all patients with NULL version values
     */
    private void initializeVersionField() {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            
            // Update all patients with NULL version to have version=0
            int updatedRows = em.createNativeQuery("UPDATE PATIENT SET VERSION = 0 WHERE VERSION IS NULL")
                .executeUpdate();
            
            em.getTransaction().commit();
            
            if (updatedRows > 0) {
                System.out.println("Initialized version field to 0 for " + updatedRows + " patients");
            }
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    /**
     * Helper method to load the existing patient from database
     */
    private PatientEntity loadExistingPatient() {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            
            PatientEntity patient = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
            assertNotNull(patient, "Test patient with ID " + EXISTING_PATIENT_ID + " must exist in database");
            
            return patient;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    /**
     * Test that verifies optimistic locking by simulating two concurrent
     * transactions trying to modify the same entity. Only one should succeed,
     * the other should throw OptimisticLockException when trying to commit changes.
     */
    @Test
    public void testOptimisticLocking() throws InterruptedException {
        // Test data
        String newPhoneNumber = "999-888-777";
        String newEmail = "updated@example.com";
        
        // When: Two threads try to update the same patient simultaneously
        CountDownLatch latch = new CountDownLatch(2); // To synchronize thread execution
        AtomicBoolean exceptionOccurred = new AtomicBoolean(false);
        
        // Thread 1 - Updates patient's phone number
        Thread thread1 = new Thread(() -> {
            EntityManager em = null;
            try {
                em = entityManagerFactory.createEntityManager();
                em.getTransaction().begin();
                
                // Find and modify patient
                PatientEntity patient1 = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
                assertEquals(initialVersion, patient1.getVersion());
                
                patient1.setTelephoneNumber(newPhoneNumber);
                em.flush(); // Force update and version increment
                
                // Wait to allow thread 2 to read the entity before commit
                Thread.sleep(500);
                
                em.getTransaction().commit();
                System.out.println("Thread 1 committed successfully. New version: " + 
                    em.find(PatientEntity.class, EXISTING_PATIENT_ID).getVersion());
            } catch (Exception e) {
                if (em != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
                latch.countDown();
            }
        });
        
        // Thread 2 - Tries to update patient's email
        Thread thread2 = new Thread(() -> {
            EntityManager em = null;
            try {
                // Wait to ensure thread 1 has started transaction
                Thread.sleep(100);
                
                em = entityManagerFactory.createEntityManager();
                em.getTransaction().begin();
                
                // Find and modify patient (with the old version value)
                PatientEntity patient2 = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
                assertEquals(initialVersion, patient2.getVersion());
                
                patient2.setEmail(newEmail);
                
                // Wait until thread 1 commits its changes
                Thread.sleep(1000);
                
                // This should throw OptimisticLockException since thread 1 already updated the entity
                em.flush();
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                if (e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException) {
                    exceptionOccurred.set(true);
                    System.out.println("Expected OptimisticLockException occurred: " + e.getMessage());
                } else {
                    e.printStackTrace();
                }
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
                latch.countDown();
            }
        });
        
        // Start both threads
        thread1.start();
        thread2.start();
        
        // Wait for both threads to complete
        latch.await();
        
        // Then: Second thread should receive OptimisticLockException
        assertTrue(exceptionOccurred.get(), "OptimisticLockException should have occurred");
        
        // And: Check that thread 1's changes were saved
        PatientEntity updatedPatient = loadExistingPatient();
        assertEquals(newPhoneNumber, updatedPatient.getTelephoneNumber(), 
                "Phone number should have been updated");
        assertEquals(initialVersion + 1, updatedPatient.getVersion(), 
                "Version should have been incremented");
        assertNotEquals(newEmail, updatedPatient.getEmail(), 
                "Email should not have been updated");
        
        // Restore original phone number to make the test idempotent
        resetPatientPhone(testPatient.getTelephoneNumber());
    }
    
    /**
     * Test that verifies version field is properly incremented when updating an entity.
     */
    @Test
    public void testVersionIncrement() {
        // Test data
        String originalFirstName = testPatient.getFirstName();
        String originalLastName = testPatient.getLastName();
        String newFirstName = "UpdatedFirstName";
        String newLastName = "UpdatedLastName";
        
        // When: We update the patient in a new transaction
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            
            PatientEntity foundPatient = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
            foundPatient.setFirstName(newFirstName);
            
            em.flush();
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        
        // Then: Version should be incremented
        PatientEntity updatedPatient = loadExistingPatient();
        assertEquals(initialVersion + 1, updatedPatient.getVersion(), 
                "Version should be incremented after update");
        assertEquals(newFirstName, updatedPatient.getFirstName(), 
                "First name should have been updated");
        
        // When: We update again in a new transaction
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            
            PatientEntity foundPatient = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
            foundPatient.setLastName(newLastName);
            
            em.flush();
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        
        // Then: Version should be incremented again
        PatientEntity twiceUpdatedPatient = loadExistingPatient();
        assertEquals(initialVersion + 2, twiceUpdatedPatient.getVersion(), 
                "Version should be incremented twice after two updates");
        assertEquals(newLastName, twiceUpdatedPatient.getLastName(), 
                "Last name should have been updated");
        
        // Restore original values to make the test idempotent
        resetPatientName(originalFirstName, originalLastName);
    }
    
    /**
     * Helper method to restore patient's original phone number
     */
    private void resetPatientPhone(String originalPhone) {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            
            PatientEntity patient = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
            patient.setTelephoneNumber(originalPhone);
            
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    /**
     * Helper method to restore patient's original first and last name
     */
    private void resetPatientName(String originalFirstName, String originalLastName) {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            
            PatientEntity patient = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
            patient.setFirstName(originalFirstName);
            patient.setLastName(originalLastName);
            
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}