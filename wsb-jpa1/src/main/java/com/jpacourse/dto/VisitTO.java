package com.jpacourse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class VisitTO implements Serializable {
    private LocalDateTime time;
    private DoctorTO doctor;
    private List<MedicalTreatmentTO> medicalTreatment;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public DoctorTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorTO doctor) {
        this.doctor = doctor;
    }

    public List<MedicalTreatmentTO> getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(List<MedicalTreatmentTO> medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }

}
