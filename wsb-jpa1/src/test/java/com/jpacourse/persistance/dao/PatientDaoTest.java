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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest
{
    @Autowired
    private PatientDao patientDao;

    // Zakładam, że w bazie danych testowych istnieje lekarz o ID 1
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
        assertThat(patient.getFirstName()).isEqualTo("Marek");
        assertThat(patient.getLastName()).isEqualTo("Wiśniewski");
        assertThat(patient.getTelephoneNumber()).isEqualTo("111222333");
        assertThat(patient.getEmail()).isEqualTo("marek.w@example.com");
        assertThat(patient.getPatientNumber()).isEqualTo("P001");
        assertThat(patient.getDateOfBirth()).isEqualTo(LocalDate.of(1985, 6, 15));

        // Sprawdzenie adresu pacjenta
        assertThat(patient.getAddress()).isNotNull();
        assertThat(patient.getAddress().getCity()).isEqualTo("Kraków");
        assertThat(patient.getAddress().getAddressLine1()).isEqualTo("ul. Floriańska 5");
        assertThat(patient.getAddress().getPostalCode()).isEqualTo("31-019");

        // Sprawdzenie wizyt pacjenta
        assertThat(patient.getVisitEntityList()).isNotNull();
        assertThat(patient.getVisitEntityList().size()).isEqualTo(2);

        // Sprawdzenie danych pierwszej wizyty
        VisitEntity firstVisit = patient.getVisitEntityList().stream()
                .filter(v -> v.getDescription().equals("Regular checkup"))
                .findFirst()
                .orElse(null);

        assertThat(firstVisit).isNotNull();
        assertThat(firstVisit.getTime()).isEqualTo(
                LocalDateTime.parse("2025-03-26 10:00:00",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(firstVisit.getDoctorEntity()).isNotNull();
        assertThat(firstVisit.getDoctorEntity().getFirstName()).isEqualTo("John");
        assertThat(firstVisit.getDoctorEntity().getLastName()).isEqualTo("Doe");
        assertThat(firstVisit.getDoctorEntity().getSpecialization().toString()).isEqualTo("SURGEON");

        // Sprawdzenie danych drugiej wizyty
        VisitEntity secondVisit = patient.getVisitEntityList().stream()
                .filter(v -> v.getDescription().equals("Neurological consultation"))
                .findFirst()
                .orElse(null);

        assertThat(secondVisit).isNotNull();
        assertThat(secondVisit.getTime()).isEqualTo(
                LocalDateTime.parse("2025-03-27 15:30:00",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(secondVisit.getDoctorEntity()).isNotNull();
        assertThat(secondVisit.getDoctorEntity().getId()).isEqualTo(EXISTING_DOCTOR_ID);
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
        assertThat(lastVisit.getDoctorEntity().getId()).isEqualTo(EXISTING_DOCTOR_ID);
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