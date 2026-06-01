package com.ecommerce.service.impl;

import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.LoginResponse;
import com.ecommerce.entity.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import com.ecommerce.dto.customer.RegisterCustomerRequest;
import com.ecommerce.dto.customer.RegisterCustomerResponse;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.enums.Role;
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
//    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {

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

//        customer.setRole(Role.CUSTOMER); already written rather than customer.setRole("CUSTOMER");
//        Did not require change

        Customer savedCustomer =
                customerRepository.save(customer);

        // Response
        return RegisterCustomerResponse.builder()
                .message(
                        "Customer registered successfully"
                )
                .customerId(
                        savedCustomer.getId()
                )
                .name(savedCustomer.getName())
                .email(savedCustomer.getEmail())
                .role(savedCustomer.getRole().name())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<Customer> customerOptional =
                customerRepository
                        .findByEmail(
                                request.getEmail()
                        );

        if (customerOptional.isEmpty()) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        Customer customer =
                customerOptional.get();

        boolean passwordMatched =
                passwordEncoder.matches(
                        request.getPassword(),
                        customer.getPassword()
                );

        if (!passwordMatched) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        return LoginResponse
                .builder()
                .customerId(
                        customer.getId()
                )
                .name(
                        customer.getName()
                )
                .email(
                        customer.getEmail()
                )
                .role(
                        customer.getRole()
                )
                .message(
                        "Login successful"
                )
                .build();
    }
}