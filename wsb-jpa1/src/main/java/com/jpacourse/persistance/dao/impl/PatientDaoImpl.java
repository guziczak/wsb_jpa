package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) {
        PatientEntity patientEntity = entityManager.find(PatientEntity.class, patientId);
        if(patientEntity == null) {
            throw new EntityNotFoundException(PatientEntity.class, patientId);
        }

        DoctorEntity doctorEntity = entityManager.find(DoctorEntity.class, doctorId);
        if(doctorEntity == null){
            throw new EntityNotFoundException(DoctorEntity.class, doctorId);
        }

        VisitEntity visit = new VisitEntity();
        visit.setTime(visitDate);
        visit.setDescription(visitDescription);
        visit.setDoctorEntity(doctorEntity);

        patientEntity.getVisitEntityList().add(visit);
        visit.setPatient(patientEntity);

        entityManager.merge(patientEntity);
    }
}
