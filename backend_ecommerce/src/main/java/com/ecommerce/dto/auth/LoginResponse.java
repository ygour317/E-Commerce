package com.ecommerce.dto.auth;

import com.ecommerce.entity.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Long customerId;

    private String name;

    private String email;

    private Role role;

    private String message;
}