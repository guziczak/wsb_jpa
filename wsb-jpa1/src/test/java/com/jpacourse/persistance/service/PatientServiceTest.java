package com.jpacourse.persistance.service;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.Specialization;
import com.jpacourse.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private PatientDao patientDao;

    @Test
    @Transactional
    public void shouldDeletePatientAndCascadeAppointmentsButNotDoctors() {
        // given
        DoctorEntity doctor = createAndSaveDoctor();
        PatientEntity patient = createAndSavePatient();
        VisitEntity visit = createAndSaveVisit(doctor, patient);

        // when
        patientService.deleteById(patient.getId());

        // then

        assertEquals(null, patientDao.findOne(patient.getId()));
        assertEquals(null, visitDao.findOne(visit.getId()));
        assertEquals(doctor, doctorDao.findOne(doctor.getId()));
    }

    private DoctorEntity createAndSaveDoctor() {
        AddressEntity address = new AddressEntity();
        address.setCity("Breslau");
        address.setAddressLine1("Stardenburdehanderbart 6");
        address.setPostalCode("193-90109");

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");
        doctor.setDoctorNumber("D01831923");
        doctor.setEmail("jan.kowalski@gmail.com");
        doctor.setTelephoneNumber("123456789");
        doctor.setAddress(address);
        doctor.setSpecialization(Specialization.DERMATOLOGIST);

        return doctorDao.save(doctor);
    }

    private PatientEntity createAndSavePatient() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setAddressLine1("Jana Pawła II");
        address.setPostalCode("53-111");

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Przemysław");
        patient.setLastName("Konieczko");
        patient.setPatientNumber("P019281");
        patient.setTelephoneNumber("987654321");
        patient.setEmail("przem.kon@gmail.com");
        patient.setAddress(address);
        patient.setDateOfBirth(LocalDate.of(1945, 7, 1));
        patient.setDateOfRegister(LocalDate.of(2025, 1, 1));

        return patientDao.save(patient);
    }

    private VisitEntity createAndSaveVisit(DoctorEntity doctor, PatientEntity patient) {
        VisitEntity visit = new VisitEntity();
        visit.setPatient(patient);
        visit.setDoctorEntity(doctor);
        visit.setTime(LocalDateTime.of(2025, 4, 11, 12, 0));

        return visitDao.save(visit);
    }
}
