package com.ecommerce.dto.response;

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