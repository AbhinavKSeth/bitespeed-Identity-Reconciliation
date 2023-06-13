package com.bitespeed.identify.controller;

import com.bitespeed.identify.entitty.IdentifyRequest;
import com.bitespeed.identify.entitty.IdentifyResponse;
import com.bitespeed.identify.service.IdentifyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bitespeed")
public class IdentityController {

    @Autowired
    private IdentifyServiceImpl identifyService;

    @PostMapping("/identify")
    public ResponseEntity<IdentifyResponse> getAllContacts(@Validated @RequestBody IdentifyRequest identifyRequest) {
        // Implement
        if  (identifyRequest ==null||(identifyRequest.getEmail()==null&& identifyRequest.getPhoneNumber()==null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Return bad request error
        }
        IdentifyResponse response = identifyService.getAllContacts(identifyRequest);
        return ResponseEntity.ok().body(response);
    }
}
