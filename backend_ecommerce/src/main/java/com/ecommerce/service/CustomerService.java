package com.ecommerce.service;

import com.ecommerce.dto.request.RegisterCustomerRequest;
import com.ecommerce.dto.response.RegisterCustomerResponse;

public interface CustomerService {

    RegisterCustomerResponse registerCustomer(
            RegisterCustomerRequest request
    );
}