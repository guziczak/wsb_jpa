package com.jpacourse.rest;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient/{id}")
    public PatientTO findById(@PathVariable final long id) {
        return patientService.findById(id);
    }

    @GetMapping("/patients")
    public List<PatientTO> findAll() {
        return patientService.findAll();
    }

    @PostMapping("/patient")
    public ResponseEntity<PatientTO> createPatient(@RequestBody final PatientTO patientTO) {
        PatientTO savedPatient = patientService.save(patientTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/patient/{id}")
    public PatientTO updatePatient(@PathVariable final long id, @RequestBody final PatientTO patientTO) {
        patientTO.setId(id);
        return patientService.update(patientTO);
    }

    @DeleteMapping("/patient/{id}")
    public void deleteById(@PathVariable final long id) {
        patientService.deleteById(id);
    }
}