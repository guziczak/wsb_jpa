package com.jpacourse.service.impl;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.mapper.DoctorMapper;
import com.jpacourse.persistance.dao.Dao;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl extends AbstractService<DoctorTO, DoctorEntity, Long> implements DoctorService {

    private final DoctorDao doctorDao;

    @Autowired
    public DoctorServiceImpl(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorTO> findAll() {
        List<DoctorEntity> doctors = doctorDao.findAll();
        return doctors.stream()
                .map(DoctorMapper::mapToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorTO findOne(Long id) {
        DoctorEntity doctor = doctorDao.findOne(id);
        return doctor != null ? DoctorMapper.mapToTO(doctor) : null;
    }

    @Override
    public DoctorTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public DoctorTO save(DoctorTO doctorTO) {
        DoctorEntity doctor = DoctorMapper.mapToEntity(doctorTO);
        DoctorEntity savedDoctor;

        if (doctor.getId() == null) {
            savedDoctor = doctorDao.save(doctor);
        } else {
            savedDoctor = doctorDao.update(doctor);
        }

        return DoctorMapper.mapToTO(savedDoctor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        doctorDao.delete(id);
    }

    @Override
    protected Dao<DoctorEntity, Long> getDao() {
        return doctorDao;
    }

    @Override
    protected DoctorTO mapToTO(DoctorEntity entity) {
        return DoctorMapper.mapToTO(entity);
    }
}