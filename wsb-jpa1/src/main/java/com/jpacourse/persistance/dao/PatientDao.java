package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);

    List<PatientEntity> findPatientsByLastName(String lastName);

}
