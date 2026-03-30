package com.murilo.portariaApp.dto.user;

import com.murilo.portariaApp.enums.Role;

public record UserRequestDTO(String name,
                             String email,
                             String password,
                             Role role,
                             Boolean active) {
}
