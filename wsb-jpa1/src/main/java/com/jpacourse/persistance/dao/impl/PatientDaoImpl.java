package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        visit.setDoctor(doctorEntity);

        patientEntity.getVisitEntityList().add(visit);
        visit.setPatient(patientEntity);

        entityManager.merge(patientEntity);
    }

    @Override
    public List<PatientEntity> findPatientsByLastName(String lastName) {
        return entityManager
                .createQuery("SELECT p FROM PatientEntity p WHERE p.lastName = :lastName", PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientWhereVisitIsMoreThan(int numberOfVisits) {
        return entityManager
                .createQuery("SELECT p FROM PatientEntity p WHERE SIZE(p.visitEntityList) > :numberOfVisits", PatientEntity.class)
                .setParameter("numberOfVisits", numberOfVisits)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientWhereDateOfRegisterIsLaterThan(LocalDate date) {
        return entityManager
                .createQuery("SELECT p FROM PatientEntity p WHERE p.dateOfRegister > :date", PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    protected Class<PatientEntity> getDomainClass() {
        return PatientEntity.class;
    }
}
