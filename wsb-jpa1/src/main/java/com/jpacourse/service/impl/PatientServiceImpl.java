package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.Dao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl extends AbstractService<PatientTO, PatientEntity, Long> implements PatientService {
    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    protected Dao<PatientEntity, Long> getDao() {
        return patientDao;
    }

    @Override
    protected PatientTO mapToTO(PatientEntity entity) {
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public void deleteById(Long id) {
        patientDao.delete(id);
    }

    @Override
    public List<PatientTO> findAll() {
        List<PatientEntity> entities = patientDao.findAll();
        return entities.stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientTO save(PatientTO patientTO) {
        PatientEntity entity = PatientMapper.mapToEntity(patientTO);
        PatientEntity savedEntity = patientDao.save(entity);
        return PatientMapper.mapToTO(savedEntity);
    }

    @Override
    public PatientTO update(PatientTO patientTO) {
        // Check if patient exists
        findById(patientTO.getId());

        PatientEntity entity = PatientMapper.mapToEntity(patientTO);
        PatientEntity updatedEntity = patientDao.update(entity);
        return PatientMapper.mapToTO(updatedEntity);
    }
}