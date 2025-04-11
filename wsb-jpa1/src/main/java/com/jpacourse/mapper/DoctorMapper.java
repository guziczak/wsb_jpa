package com.jpacourse.mapper;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.persistance.entity.DoctorEntity;

public class DoctorMapper {

    public static DoctorTO mapToTO(final DoctorEntity doctorEntity) {
        if(doctorEntity == null) {
            return null;
        }

        final DoctorTO doctor = new DoctorTO();
        doctor.setFirstName(doctorEntity.getFirstName());
        doctor.setLastName(doctorEntity.getLastName());
        return doctor;
    }
}
