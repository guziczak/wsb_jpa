package com.jpacourse.persistance.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "PATIENT")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FIRSTNAME", nullable = false)
	private String firstName;

	@Column(name = "LASTNAME", nullable = false)
	private String lastName;

	@Column(name = "TELEPHONENUMBER", nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(name = "PATIENTNUMBER", nullable = false)
	private String patientNumber;

	@Column(name = "DATEOFBIRTH", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "DATE_OF_REGISTER")
	private LocalDate dateOfRegister;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
	private AddressEntity address;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisitEntity> visitEntityList;

	// Gettery i settery

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(LocalDate dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public List<VisitEntity> getVisitEntityList() {
		return visitEntityList;
	}

	public void setVisitEntityList(List<VisitEntity> visitEntityList) {
		this.visitEntityList = visitEntityList;
	}
}