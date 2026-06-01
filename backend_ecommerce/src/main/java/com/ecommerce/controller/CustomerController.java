package com.ecommerce.controller;

import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.LoginResponse;

import com.ecommerce.dto.customer.RegisterCustomerRequest;
import com.ecommerce.dto.customer.RegisterCustomerResponse;
import com.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterCustomerResponse> registerCustomer(
            @Valid
            @RequestBody
            RegisterCustomerRequest request) {

        RegisterCustomerResponse response =
                customerService
                        .registerCustomer(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return ResponseEntity.ok(
                customerService.login(
                        request
                )
        );
    }
}