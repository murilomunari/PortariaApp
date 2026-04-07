package com.murilo.portariaApp.dto.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitRequestDTO(
        @NotBlank(message = "O número do apartamento é obrigatório")
        @Size(max = 20)
        String number,

        @Size(max = 50)
        String block,

        Integer floor,

        @Size(max = 255)
        String description
) {
}
