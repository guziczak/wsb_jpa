package com.jpacourse.rest;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {
    private final PatientService patientService;
    public PatientController(PatientService patientService){this.patientService = patientService;}

    @GetMapping("/patient/{id}")
    public PatientTO findById(@PathVariable final long id) {
        final PatientTO patientTO = patientService.findById(id);

        if(patientTO == null) return patientTO;

        throw new EntityNotFoundException(id);
    }
}
