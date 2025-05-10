package com.jpacourse.mapper;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.persistance.entity.DoctorEntity;

public class DoctorMapper {

    public static DoctorTO mapToTO(final DoctorEntity doctorEntity) {
        if(doctorEntity == null) {
            return null;
        }

        final DoctorTO doctor = new DoctorTO();
        doctor.setId(doctorEntity.getId());
        doctor.setFirstName(doctorEntity.getFirstName());
        doctor.setLastName(doctorEntity.getLastName());
        doctor.setTelephoneNumber(doctorEntity.getTelephoneNumber());
        doctor.setEmail(doctorEntity.getEmail());
        doctor.setDoctorNumber(doctorEntity.getDoctorNumber());
        doctor.setSpecialization(doctorEntity.getSpecialization());
        doctor.setAddress(AddressMapper.mapToTO(doctorEntity.getAddress()));

        return doctor;
    }

    public static DoctorEntity mapToEntity(final DoctorTO doctorTO) {
        if(doctorTO == null) {
            return null;
        }

        final DoctorEntity doctor = new DoctorEntity();
        doctor.setId(doctorTO.getId());
        doctor.setFirstName(doctorTO.getFirstName());
        doctor.setLastName(doctorTO.getLastName());
        doctor.setTelephoneNumber(doctorTO.getTelephoneNumber());
        doctor.setEmail(doctorTO.getEmail());
        doctor.setDoctorNumber(doctorTO.getDoctorNumber());
        doctor.setSpecialization(doctorTO.getSpecialization());
        doctor.setAddress(AddressMapper.mapToEntity(doctorTO.getAddress()));

        return doctor;
    }
}
