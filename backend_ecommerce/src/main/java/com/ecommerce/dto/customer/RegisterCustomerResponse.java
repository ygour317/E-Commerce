package com.ecommerce.dto.customer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterCustomerResponse {

    private String message;
    private Long customerId;
    private String name;
    private String email;
    private String role;
}