package com.jpacourse.rest;

import com.jpacourse.dto.DoctorTO;
import com.jpacourse.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorTO>> getAllDoctors() {
        List<DoctorTO> doctors = doctorService.findAll();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorTO> getDoctorById(@PathVariable Long id) {
        DoctorTO doctor = doctorService.findById(id);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<DoctorTO> createDoctor(@RequestBody DoctorTO doctorTO) {
        DoctorTO savedDoctor = doctorService.save(doctorTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorTO doctorTO) {
        doctorService.findById(id);

        doctorTO.setId(id);
        DoctorTO updatedDoctor = doctorService.save(doctorTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.findById(id);

        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}