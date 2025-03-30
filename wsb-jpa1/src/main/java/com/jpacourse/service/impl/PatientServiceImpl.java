package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.AddressDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.AddressService;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private PatientDao patientDao;
    private final VisitDao visitDao;
    private final PatientService patientService;

    @Autowired
    public PatientServiceImpl(VisitDao visitDao, PatientService patientService) {
        this.visitDao = visitDao;
        this.patientService = patientService;
    }

    @Transactional
    @Override
    public PatientTO findById(Long patientId) {
        final List<VisitEntity> visits = visitDao.findByPatientId(patientId);

        PatientTO patientTO = PatientMapper.mapToTO(visits.get(0).getPatientEntity());
        if(patientTO!=null) {
            patientTO.setVisits(
                    visits.isEmpty()
                            ? Collections.emptyList()
                            : visits.stream().map(VisitMapper::mapToTO).toList()
            );
        }

        return patientTO;
    }

}
