package com.jpacourse.persistance.dao;

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

    @Test
    @Transactional
    public void testFindPatientsByLastName() {
        String existingLastName = "Lewandowski";

        List<PatientEntity> patients = patientDao.findPatientsByLastName(existingLastName);

        assertFalse(patients.isEmpty(), "Should find patients with lastName: " + existingLastName);

        for (PatientEntity patient : patients) {
            assertEquals(existingLastName, patient.getLastName(),
                    "All found patients should have lastName: " + existingLastName);
        }

        String nonExistentLastName = "NonExistentName";
        List<PatientEntity> emptyResult = patientDao.findPatientsByLastName(nonExistentLastName);

        assertNotNull(emptyResult, "Query result should not be null for non-existent lastName");
        assertTrue(emptyResult.isEmpty(), "Should not find patients with non-existent lastName");
    }

    @Test
    @Transactional
    public void testFindAllVisitsByPatientId() {
        Long patientId = 3L;

        List<VisitTO> visits = visitService.findByPatientId(patientId);

        assertFalse(visits.isEmpty(), "Should find visits for patient with ID: " + patientId);

        for (VisitTO visit : visits) {
            assertNotNull(visit.getTime(), "Visit time should not be null");
            assertNotNull(visit.getDoctor(), "Visit doctor should not be null");
        }

        Long nonExistentPatientId = 999L;
        List<VisitTO> emptyResult = visitService.findByPatientId(nonExistentPatientId);

        assertNotNull(emptyResult, "Query result should not be null for non-existent patient ID");
        assertTrue(emptyResult.isEmpty(), "Should not find visits for non-existent patient ID");
    }

    @Test
    @Transactional
    public void testFindPatientsWithMoreThanXVisits() {
        int visitThreshold = 3;

        List<PatientEntity> patients = patientDao.findPatientWhereVisitIsMoreThan(visitThreshold);

        assertFalse(patients.isEmpty(), "Should find patients with more than " + visitThreshold + " visits");

        for (PatientEntity patient : patients) {
            List<VisitEntity> visits = patient.getVisitEntityList();
            assertTrue(visits.size() > visitThreshold,
                    "Patient should have more than " + visitThreshold + " visits, but has " + visits.size());
        }

        int highThreshold = 100;
        List<PatientEntity> emptyResult = patientDao.findPatientWhereVisitIsMoreThan(highThreshold);

        assertNotNull(emptyResult, "Query result should not be null for high visit threshold");
        assertTrue(emptyResult.isEmpty(), "Should not find patients with more than " + highThreshold + " visits");
    }

    @Test
    @Transactional
    public void testFindPatientsWithRegistrationDateAfter() {
        LocalDate cutoffDate = LocalDate.of(2023, 12, 31);
        
        List<PatientEntity> patients = patientDao.findPatientWhereDateOfRegisterIsLaterThan(cutoffDate);
        
        assertFalse(patients.isEmpty(), "Should find patients registered after " + cutoffDate);
        
        for (PatientEntity patient : patients) {
            LocalDate registerDate = patient.getDateOfRegister();
            assertNotNull(registerDate, "Registration date should not be null");
            assertTrue(registerDate.isAfter(cutoffDate), 
                    "Patient register date " + registerDate + " should be after cutoff " + cutoffDate);
        }
        
        LocalDate futureDate = LocalDate.now().plusYears(1);
        List<PatientEntity> emptyResult = patientDao.findPatientWhereDateOfRegisterIsLaterThan(futureDate);
        
        assertNotNull(emptyResult, "Query result should not be null for future date cutoff");
        assertTrue(emptyResult.isEmpty(), "Should not find patients registered after " + futureDate);
    }
}