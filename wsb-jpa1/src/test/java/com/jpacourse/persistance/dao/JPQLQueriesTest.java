package com.jpacourse.persistance.dao;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import com.jpacourse.service.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the JPQL queries required in Lab 3:
 * 1. Find patients by lastName (DAO test)
 * 2. Find all visits of a patient by patient ID (Service test)
 * 3. Find patients with more than X visits (DAO test)
 * 4. Find patients where dateOfRegister is later than a given date (DAO test)
 */
@SpringBootTest
public class JPQLQueriesTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private PatientService patientService;

    @Autowired
    private VisitService visitService;

    /**
     * Test case for finding patients by lastName
     * This is the DAO test required for query #1 in Lab 3
     */
    @Test
    @Transactional
    public void testFindPatientsByLastName() {
        // Given - "Lewandowski" last name exists in our test data
        String existingLastName = "Lewandowski";

        // When - Query is executed
        List<PatientEntity> patients = patientDao.findPatientsByLastName(existingLastName);

        // Then - Should find at least one patient with this lastName
        assertFalse(patients.isEmpty(), "Should find patients with lastName: " + existingLastName);

        // Verify all found patients have the correct lastName
        for (PatientEntity patient : patients) {
            assertEquals(existingLastName, patient.getLastName(),
                    "All found patients should have lastName: " + existingLastName);
        }

        // Test with a non-existent lastName
        String nonExistentLastName = "NonExistentName";
        List<PatientEntity> emptyResult = patientDao.findPatientsByLastName(nonExistentLastName);

        // Should return an empty list, not null
        assertNotNull(emptyResult, "Query result should not be null for non-existent lastName");
        assertTrue(emptyResult.isEmpty(), "Should not find patients with non-existent lastName");
    }

    /**
     * Test case for finding all visits of a patient by patient ID
     * This is the Service test required for query #2 in Lab 3
     */
    @Test
    @Transactional
    public void testFindAllVisitsByPatientId() {
        // Given - Patient with ID=3 has multiple visits in our test data
        Long patientId = 3L;

        // When - Query is executed through service layer
        List<VisitTO> visits = visitService.findByPatientId(patientId);

        // Then - Should find at least one visit for this patient
        assertFalse(visits.isEmpty(), "Should find visits for patient with ID: " + patientId);

        // Verify proper visit data mapping
        for (VisitTO visit : visits) {
            assertNotNull(visit.getTime(), "Visit time should not be null");
            assertNotNull(visit.getDoctor(), "Visit doctor should not be null");
        }

        // Test with a non-existent patient ID
        Long nonExistentPatientId = 999L;
        List<VisitTO> emptyResult = visitService.findByPatientId(nonExistentPatientId);

        // Should return an empty list, not null
        assertNotNull(emptyResult, "Query result should not be null for non-existent patient ID");
        assertTrue(emptyResult.isEmpty(), "Should not find visits for non-existent patient ID");
    }

    /**
     * Test case for finding patients with more than X visits
     * This is the DAO test required for query #3 in Lab 3
     */
    @Test
    @Transactional
    public void testFindPatientsWithMoreThanXVisits() {
        // Given - We know patient with ID=3 has multiple visits (5+)
        int visitThreshold = 3;

        // When - Query is executed
        List<PatientEntity> patients = patientDao.findPatientWhereVisitIsMoreThan(visitThreshold);

        // Then - Should find at least one patient with more than 3 visits
        assertFalse(patients.isEmpty(), "Should find patients with more than " + visitThreshold + " visits");

        // Verify each patient actually has more than the threshold number of visits
        for (PatientEntity patient : patients) {
            List<VisitEntity> visits = patient.getVisitEntityList();
            assertTrue(visits.size() > visitThreshold,
                    "Patient should have more than " + visitThreshold + " visits, but has " + visits.size());
        }

        // Test with a very high threshold that should return no results
        int highThreshold = 100;
        List<PatientEntity> emptyResult = patientDao.findPatientWhereVisitIsMoreThan(highThreshold);

        // Should return an empty list, not null
        assertNotNull(emptyResult, "Query result should not be null for high visit threshold");
        assertTrue(emptyResult.isEmpty(), "Should not find patients with more than " + highThreshold + " visits");
    }

    /**
     * Test case for finding patients where dateOfRegister is later than a given date
     * This is the DAO test required for query #4 in Lab 3
     */
    @Test
    @Transactional
    public void testFindPatientsWithRegistrationDateAfter() {
        // Given - We know there are patients registered in 2024
        LocalDate cutoffDate = LocalDate.of(2023, 12, 31);
        
        // When - Query is executed
        List<PatientEntity> patients = patientDao.findPatientWhereDateOfRegisterIsLaterThan(cutoffDate);
        
        // Then - Should find at least one patient registered after the cutoff date
        assertFalse(patients.isEmpty(), "Should find patients registered after " + cutoffDate);
        
        // Verify all found patients have registration dates after the cutoff
        for (PatientEntity patient : patients) {
            LocalDate registerDate = patient.getDateOfRegister();
            assertNotNull(registerDate, "Registration date should not be null");
            assertTrue(registerDate.isAfter(cutoffDate), 
                    "Patient register date " + registerDate + " should be after cutoff " + cutoffDate);
        }
        
        // Test with a future date that should return no results
        LocalDate futureDate = LocalDate.now().plusYears(1);
        List<PatientEntity> emptyResult = patientDao.findPatientWhereDateOfRegisterIsLaterThan(futureDate);
        
        // Should return an empty list, not null
        assertNotNull(emptyResult, "Query result should not be null for future date cutoff");
        assertTrue(emptyResult.isEmpty(), "Should not find patients registered after " + futureDate);
    }
}