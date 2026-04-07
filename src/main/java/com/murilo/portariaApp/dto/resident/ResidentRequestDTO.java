package com.murilo.portariaApp.dto.resident;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ResidentRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150)
        String name,

        @NotBlank(message = "Telefone é obrigatório")
        @Size(max = 20)
        String phone,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        @Size(max = 150)
        String email,

        @NotNull(message = "ID da unidade é obrigatório")
        UUID unitId
) {
}
