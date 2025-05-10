package com.jpacourse.persistance.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "patient")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@Column(name = "telephonenumber", nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(name = "patientnumber", nullable = false)
	private String patientNumber;

	@Column(name = "dateofbirth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "date_of_register")
	private LocalDate dateOfRegister;

	@Version
	private Long version;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", unique = true)
	private AddressEntity address;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}