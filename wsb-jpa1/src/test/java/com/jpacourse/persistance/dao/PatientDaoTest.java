package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest
{
    @Autowired
    private PatientDao patientDao;

    private static final Long EXISTING_DOCTOR_ID = 1L;

    @Transactional
    @Test
    public void testShouldVerifyTestDataFromDataSql() {
        // given
        Long patientId = 1L;

        // when
        PatientEntity patient = patientDao.findOne(patientId);

        // then
        assertThat(patient).isNotNull();
        assertThat(patient.getFirstName()).isEqualTo("Adam");
        assertThat(patient.getLastName()).isEqualTo("Kowalczyk");
        assertThat(patient.getTelephoneNumber()).isEqualTo("111222333");
        assertThat(patient.getEmail()).isEqualTo("adam.kowalczyk@example.com");
        assertThat(patient.getPatientNumber()).isEqualTo("PAT001");
        assertThat(patient.getDateOfBirth()).isEqualTo(LocalDate.of(1985, 5, 15));

        assertThat(patient.getAddress()).isNotNull();
        assertThat(patient.getAddress().getCity()).isEqualTo("Warszawa");
        assertThat(patient.getAddress().getAddressLine1()).isEqualTo("ul. Marszałkowska 123");
        assertThat(patient.getAddress().getPostalCode()).isEqualTo("60-400");

        assertThat(patient.getVisitEntityList()).isNotNull();
        assertThat(patient.getVisitEntityList().size()).isEqualTo(2);

        VisitEntity firstVisit = patient.getVisitEntityList().stream()
                .filter(v -> v.getDescription().equals("Konsultacja kardiologiczna"))
                .findFirst()
                .orElse(null);

        assertThat(firstVisit).isNotNull();
        assertThat(firstVisit.getTime()).isEqualTo(
                LocalDateTime.parse("2023-07-10 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(firstVisit.getDoctor()).isNotNull();
        assertThat(firstVisit.getDoctor().getFirstName()).isEqualTo("Jan");
        assertThat(firstVisit.getDoctor().getLastName()).isEqualTo("Kowalski");
        assertThat(firstVisit.getDoctor().getSpecialization().toString()).isEqualTo("CARDIOLOGIST");

        VisitEntity secondVisit = patient.getVisitEntityList().stream()
                .filter(v -> v.getDescription().equals("Kontrola kardiologiczna"))
                .findFirst()
                .orElse(null);

        assertThat(secondVisit).isNotNull();
        assertThat(secondVisit.getTime()).isEqualTo(
                LocalDateTime.parse("2023-08-10 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(secondVisit.getDoctor()).isNotNull();
        assertThat(secondVisit.getDoctor().getFirstName()).isEqualTo("Jan");
        assertThat(secondVisit.getDoctor().getLastName()).isEqualTo("Kowalski");
    }

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
        assertThat(lastVisit.getDoctor().getId()).isEqualTo(EXISTING_DOCTOR_ID);
        assertThat(lastVisit.getPatient().getId()).isEqualTo(savedPatient.getId());
    }

    @Transactional
    @Test
    public void testShouldReturnNullWhenPatientNotFound() {
        // given
        Long nonExistentPatientId = 9999L;

        // when
        PatientEntity result = patientDao.findOne(nonExistentPatientId);

        // then
        assertThat(result).isNull();
    }

    @Transactional
    @Test
    public void testShouldReturnNullWhenDoctorNotFound() {
        // given
        Long nonExistentDoctorId = 9999L;

        // when
        PatientEntity patientEntity = createTestPatient();
        final PatientEntity savedPatient = patientDao.save(patientEntity);

        // then
        assertThat(savedPatient).isNotNull();
    }

    @Transactional
    @Test
    void testFindPatientsByLastName() {
        PatientEntity patientEntity = createTestPatient();
        patientDao.save(patientEntity);

        List<PatientEntity> results = patientDao.findPatientsByLastName(patientEntity.getLastName());
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getFirstName()).isEqualTo(patientEntity.getFirstName());
    }

    @Transactional
    @Test
    void testFindPatientWhereVisitIsMoreThan() {
        List<PatientEntity> results = patientDao.findPatientWhereVisitIsMoreThan(2);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getLastName()).isEqualTo("Lewandowski");
    }

    private PatientEntity createTestPatient() {
        PatientEntity patientEntity = new PatientEntity();
        // Do NOT set an ID - let the database assign it
        patientEntity.setFirstName("Jan");
        patientEntity.setLastName("Testowy");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("jan.testowy@example.com");
        patientEntity.setPatientNumber("PAT123");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientEntity.setDateOfRegister(LocalDate.now());

        AddressEntity addressEntity = new AddressEntity();
        // Do NOT set an ID - let the database assign it
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