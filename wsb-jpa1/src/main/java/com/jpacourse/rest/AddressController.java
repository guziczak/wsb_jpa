package com.jpacourse.rest;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address/{id}")
    AddressTO findBaId(@PathVariable final Long id) {
        return addressService.findById(id);
    }

}
