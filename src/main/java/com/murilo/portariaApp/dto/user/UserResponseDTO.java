package com.murilo.portariaApp.dto.user;

import com.murilo.portariaApp.enums.Role;

public record UserResponseDTO(String name,
                              String email,
                              Role role) {
}
