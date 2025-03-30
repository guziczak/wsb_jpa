package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.VisitEntity;

import java.util.List;

public interface VisitDao extends Dao<VisitEntity, Long> {
    public List<VisitEntity> findByPatientId(Long idPatient);

}
