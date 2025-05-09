package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {
    PatientTO findById(final Long id);

    List<PatientTO> findAll();

    List<PatientTO> findPatientWhereDateOfRegisterIsLaterThan(LocalDate date);

    PatientTO save(final PatientTO patientTO);

    PatientTO update(final PatientTO patientTO);

    void deleteById(final Long id);

    List<PatientTO> findByLastName(final String lastName);

    List<PatientTO> findPatientWhereVisitIsMoreThan(int numberOfVisits);
}