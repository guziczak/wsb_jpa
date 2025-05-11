package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class OptimisticLockingTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    
    private static final Long EXISTING_PATIENT_ID = 3L;
    
    private PatientEntity testPatient;
    private Long initialVersion;
    
    @BeforeEach
    public void setUp() {
        initializeVersionField();
        
        testPatient = loadExistingPatient();
        initialVersion = testPatient.getVersion();
        
        assertNotNull(initialVersion, "Version field must not be null");
    }

    private void initializeVersionField() {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            
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
    
    @Test
    public void testOptimisticLocking() throws InterruptedException {
        String newPhoneNumber = "999-888-777";
        String newEmail = "updated@example.com";
        
        CountDownLatch latch = new CountDownLatch(2); // To synchronize thread execution
        AtomicBoolean exceptionOccurred = new AtomicBoolean(false);
        
        Thread thread1 = new Thread(() -> {
            EntityManager em = null;
            try {
                em = entityManagerFactory.createEntityManager();
                em.getTransaction().begin();
                
                PatientEntity patient1 = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
                assertEquals(initialVersion, patient1.getVersion());
                
                patient1.setTelephoneNumber(newPhoneNumber);
                em.flush();
                
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
        
        Thread thread2 = new Thread(() -> {
            EntityManager em = null;
            try {
                Thread.sleep(100);
                
                em = entityManagerFactory.createEntityManager();
                em.getTransaction().begin();
                
                PatientEntity patient2 = em.find(PatientEntity.class, EXISTING_PATIENT_ID);
                assertEquals(initialVersion, patient2.getVersion());
                
                patient2.setEmail(newEmail);
                
                Thread.sleep(1000);
                
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
        
        thread1.start();
        thread2.start();
        
        latch.await();
        
        assertTrue(exceptionOccurred.get(), "OptimisticLockException should have occurred");
        
        PatientEntity updatedPatient = loadExistingPatient();
        assertEquals(newPhoneNumber, updatedPatient.getTelephoneNumber(), 
                "Phone number should have been updated");
        assertEquals(initialVersion + 1, updatedPatient.getVersion(), 
                "Version should have been incremented");
        assertNotEquals(newEmail, updatedPatient.getEmail(), 
                "Email should not have been updated");
        
        resetPatientPhone(testPatient.getTelephoneNumber());
    }
    
    @Test
    public void testVersionIncrement() {
        String originalFirstName = testPatient.getFirstName();
        String originalLastName = testPatient.getLastName();
        String newFirstName = "UpdatedFirstName";
        String newLastName = "UpdatedLastName";
        
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
        
        PatientEntity updatedPatient = loadExistingPatient();
        assertEquals(initialVersion + 1, updatedPatient.getVersion(), 
                "Version should be incremented after update");
        assertEquals(newFirstName, updatedPatient.getFirstName(), 
                "First name should have been updated");
        
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
        
        PatientEntity twiceUpdatedPatient = loadExistingPatient();
        assertEquals(initialVersion + 2, twiceUpdatedPatient.getVersion(), 
                "Version should be incremented twice after two updates");
        assertEquals(newLastName, twiceUpdatedPatient.getLastName(), 
                "Last name should have been updated");
        
        resetPatientName(originalFirstName, originalLastName);
    }
    
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