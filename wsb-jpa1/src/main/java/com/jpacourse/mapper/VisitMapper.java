package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.VisitEntity;

import java.util.ArrayList;
import java.util.List;

public class VisitMapper {
    public static List<VisitTO> mapToTO(List<VisitEntity> visitEntityList) {
        List<VisitTO> visitList = new ArrayList<>();

        if (visitEntityList == null) {
            return visitList; // Zwracamy pustą listę zamiast rzucać wyjątek
        }

        for (VisitEntity visitEntity : visitEntityList) {
            VisitTO visit = new VisitTO();
            visit.setTime(visitEntity.getTime());
            visit.setDoctor(DoctorMapper.mapToTO(visitEntity.getDoctor()));
            visit.setMedicalTreatment(MedicalTreatmentMapper.mapToTO(visitEntity.getMedicalTreatmentEntityList()));
            visitList.add(visit);
        }

        return visitList;
    }

    public static VisitTO mapToTO(VisitEntity visitEntity) {
        VisitTO visit = new VisitTO();
        visit.setTime(visitEntity.getTime());
        visit.setDoctor(DoctorMapper.mapToTO(visitEntity.getDoctor()));
        visit.setMedicalTreatment(MedicalTreatmentMapper.mapToTO(visitEntity.getMedicalTreatmentEntityList()));

        return visit;
    }
}