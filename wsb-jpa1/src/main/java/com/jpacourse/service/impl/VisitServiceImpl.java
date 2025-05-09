package com.jpacourse.service.impl;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.Dao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VisitServiceImpl extends AbstractService<VisitTO, VisitEntity, Long> implements VisitService {
    private final VisitDao visitDao;

    @Autowired
    public VisitServiceImpl(VisitDao visitDao) {this.visitDao = visitDao;}

    @Override
    protected Dao<VisitEntity, Long> getDao() {
        return visitDao;
    }

    @Override
    protected VisitTO mapToTO(VisitEntity entity) {
        return VisitMapper.mapToTO(entity);
    }

    @Override
    public List<VisitTO> findByPatientId(int patientId) {
        return VisitMapper.mapToTO(visitDao.findVisitByPatientId(patientId));
    }
}
