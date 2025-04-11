package com.jpacourse.dto;

import com.jpacourse.persistance.enums.TreatmentType;

import java.io.Serializable;

public class MedicalTreatmentTO implements Serializable {
    private TreatmentType treatmentType;

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }
}
