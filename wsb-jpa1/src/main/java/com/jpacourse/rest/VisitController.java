package com.jpacourse.rest;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.service.VisitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {this.visitService = visitService;}

    @GetMapping("/find-by-patientId/{patientId}")
    public List<VisitTO> getAllVisitsByPatientId(@PathVariable final int patientId) {
        return visitService.findByPatientId(patientId);
    }
}
