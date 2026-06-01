package com.ecommerce.service;

import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.LoginResponse;
import com.ecommerce.dto.customer.RegisterCustomerRequest;
import com.ecommerce.dto.customer.RegisterCustomerResponse;

public interface CustomerService {

    RegisterCustomerResponse registerCustomer(
            RegisterCustomerRequest request
    );

    LoginResponse login(
            LoginRequest request
    );
}