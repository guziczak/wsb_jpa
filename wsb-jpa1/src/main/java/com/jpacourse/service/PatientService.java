package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.util.List;

public interface PatientService {
    PatientTO findById(final Long id);

    List<PatientTO> findAll();

    PatientTO save(final PatientTO patientTO);

    PatientTO update(final PatientTO patientTO);

    void deleteById(final Long id);

    List<PatientTO> findByLastName(final String lastName);
}