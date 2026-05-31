package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RegisterCustomerRequest;
import com.ecommerce.dto.response.RegisterCustomerResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Role;
import com.ecommerce.exception.EmailAlreadyExistsException;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public RegisterCustomerResponse registerCustomer(
            RegisterCustomerRequest request
    ) {

        // Check unique email
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists"
            );
        }

        // Password match validation
        if (!request.getPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException(
                    "Password and Confirm Password do not match"
            );
        }

        // Save customer
        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.CUSTOMER)
                .build();

        Customer savedCustomer =
                customerRepository.save(customer);

        // Response
        return RegisterCustomerResponse.builder()
                .message(
                        "Customer registered successfully"
                )
                .customerId(
                        savedCustomer.getCustomerId()
                )
                .name(savedCustomer.getName())
                .email(savedCustomer.getEmail())
                .role(savedCustomer.getRole().name())
                .build();
    }
}