package com.ecommerce.controller;

import com.ecommerce.dto.request.RegisterCustomerRequest;
import com.ecommerce.dto.response.RegisterCustomerResponse;
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
}