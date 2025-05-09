package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.PatientEntity;

import java.util.ArrayList;
import java.util.List;

public class PatientMapper {
    public static List<PatientTO> mapToTO(final List<PatientEntity> patientEntityList) {
        List<PatientTO> resultPatientList = new ArrayList<>();
        patientEntityList.forEach(p ->
                resultPatientList.add(mapToTO(p))
        );

        return resultPatientList;
    }

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null)
        {
            return null;
        }
        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setDateOfRegister(patientEntity.getDateOfRegister());
        patientTO.setAddress(AddressMapper.mapToTO(patientEntity.getAddress()));
        patientTO.setVisits(VisitMapper.mapToTO(patientEntity.getVisitEntityList()));
        return patientTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO)
    {
        if(patientTO == null)
        {
            return null;
        }
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setDateOfRegister(patientTO.getDateOfRegister());
        patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));
        return patientEntity;
    }
}
