package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TIME", nullable = false)
	private LocalDateTime time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCTOR_ID", referencedColumnName = "ID")
	private DoctorEntity doctor;

	@ManyToOne()
	@JoinColumn(name = "PATIENT_ID")
	private PatientEntity patient;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "VISIT_ID")
	private List<MedicalTreatmentEntity> medicalTreatmentEntityList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public List<MedicalTreatmentEntity> getMedicalTreatmentEntityList() {
		return medicalTreatmentEntityList;
	}

	public void setMedicalTreatmentEntityList(List<MedicalTreatmentEntity> medicalTreatmentEntityList) {
		this.medicalTreatmentEntityList = medicalTreatmentEntityList;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

}
