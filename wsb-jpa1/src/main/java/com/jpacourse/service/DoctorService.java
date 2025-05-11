package com.jpacourse.service;

import com.jpacourse.dto.DoctorTO;
import java.util.List;

public interface DoctorService {

    List<DoctorTO> findAll();

    DoctorTO findById(Long id);

    DoctorTO findOne(Long id);

    DoctorTO save(DoctorTO doctorTO);

    void delete(Long id);
}