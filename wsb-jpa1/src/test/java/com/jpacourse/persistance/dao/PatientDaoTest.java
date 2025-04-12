package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PatientDaoTest
{
    @Autowired
    private PatientDao patientDao;

    // Zakładam, że w bazie danych testowych istnieje lekarz o ID 1
    private static final Long EXISTING_DOCTOR_ID = 1L;

    @Transactional
    @Test
    public void testShouldSavePatient() {
        // given
        PatientEntity patientEntity = createTestPatient();
        long entitiesNumBefore = patientDao.count();

        // when
        final PatientEntity saved = patientDao.save(patientEntity);

        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(patientDao.count()).isEqualTo(entitiesNumBefore + 1);
    }

    @Transactional
    @Test
    public void testShouldSaveAndRemovePatient() {
        // given
        PatientEntity patientEntity = createTestPatient();

        // when
        final PatientEntity saved = patientDao.save(patientEntity);
        assertThat(saved.getId()).isNotNull();
        final PatientEntity newSaved = patientDao.findOne(saved.getId());
        assertThat(newSaved).isNotNull();

        patientDao.delete(saved.getId());

        // then
        final PatientEntity removed = patientDao.findOne(saved.getId());
        assertThat(removed).isNull();
    }

    @Transactional
    @Test
    public void testShouldAddVisitToPatient() {
        // given
        PatientEntity patientEntity = createTestPatient();
        final PatientEntity savedPatient = patientDao.save(patientEntity);

        LocalDateTime visitDate = LocalDateTime.now();
        String visitDescription = "Wizyta kontrolna";
        int initialVisitCount = 0;
        if (savedPatient.getVisitEntityList() != null) {
            initialVisitCount = savedPatient.getVisitEntityList().size();
        }

        // when
        patientDao.addVisitToPatient(savedPatient.getId(), EXISTING_DOCTOR_ID, visitDate, visitDescription);

        // then
        PatientEntity updatedPatient = patientDao.findOne(savedPatient.getId());
        assertThat(updatedPatient.getVisitEntityList()).isNotNull();
        assertThat(updatedPatient.getVisitEntityList().size()).isEqualTo(initialVisitCount + 1);

        VisitEntity lastVisit = updatedPatient.getVisitEntityList().get(updatedPatient.getVisitEntityList().size() - 1);
        assertThat(lastVisit.getDescription()).isEqualTo(visitDescription);
        assertThat(lastVisit.getTime()).isEqualTo(visitDate);
        assertThat(lastVisit.getDoctorEntity().getId()).isEqualTo(EXISTING_DOCTOR_ID);
        assertThat(lastVisit.getPatient().getId()).isEqualTo(savedPatient.getId());
    }

    @Transactional
    @Test
    public void testShouldThrowExceptionWhenPatientNotFound() {
        // given
        Long nonExistentPatientId = 9999L;
        LocalDateTime visitDate = LocalDateTime.now();
        String visitDescription = "Wizyta kontrolna";

        // when & then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            patientDao.addVisitToPatient(nonExistentPatientId, EXISTING_DOCTOR_ID, visitDate, visitDescription);
        });
    }

    @Transactional
    @Test
    public void testShouldThrowExceptionWhenDoctorNotFound() {
        // given
        PatientEntity patientEntity = createTestPatient();
        final PatientEntity savedPatient = patientDao.save(patientEntity);

        Long nonExistentDoctorId = 9999L;
        LocalDateTime visitDate = LocalDateTime.now();
        String visitDescription = "Wizyta kontrolna";

        // when & then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            patientDao.addVisitToPatient(savedPatient.getId(), nonExistentDoctorId, visitDate, visitDescription);
        });
    }

    private PatientEntity createTestPatient() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Jan");
        patientEntity.setLastName("Testowy");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("jan.testowy@example.com");
        patientEntity.setPatientNumber("PAT123");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientEntity.setDateOfRegister(LocalDate.now());

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("ul. Testowa 1");
        addressEntity.setAddressLine2("m. 123");
        addressEntity.setCity("Warszawa");
        addressEntity.setPostalCode("00-001");

        patientEntity.setAddress(addressEntity);
        addressEntity.setPatient(patientEntity);

        patientEntity.setVisitEntityList(new ArrayList<>());

        return patientEntity;
    }
}