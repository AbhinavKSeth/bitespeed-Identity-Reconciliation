package com.bitespeed.identify.controller;

import com.bitespeed.identify.entitty.IdentityRequest;
import com.bitespeed.identify.entitty.IdentityResponse;
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
    public ResponseEntity<IdentityResponse> getAllContacts(@Validated @RequestBody IdentityRequest identityRequest) {
        // Implement
        if  (identityRequest==null||(identityRequest.getEmail()==null&&identityRequest.getPhoneNumber()==null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Return bad request error
        }
        IdentityResponse response = identifyService.getAllContacts(identityRequest);
        return ResponseEntity.ok().body(response);
    }
}
