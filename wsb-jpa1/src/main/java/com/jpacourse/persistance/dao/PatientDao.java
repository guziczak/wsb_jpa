package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDateTime;

public interface PatientDao extends Dao<PatientEntity, Long>{
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);

}
