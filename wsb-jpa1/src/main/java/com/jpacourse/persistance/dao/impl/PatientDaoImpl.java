package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) {
        PatientEntity patientEntity = entityManager.find(PatientEntity.class, patientId);
        if(patientEntity == null) throw new IllegalArgumentException("Pacjent o podanym ID nie istnieje");

        DoctorEntity doctorEntity = entityManager.find(DoctorEntity.class, doctorId);
        if(doctorEntity == null) throw new IllegalArgumentException("Doktor o podanym ID nie istnieje");

        VisitEntity visit = new VisitEntity();
        visit.setTime(visitDate);
        visit.setDescription(visitDescription);
        visit.setDoctorEntity(doctorEntity);

        patientEntity.getVisitEntityList().add(visit);
        visit.setPatient(patientEntity);

        entityManager.merge(patientEntity);
    }
}
