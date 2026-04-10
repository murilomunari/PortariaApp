package com.murilo.portariaApp.dto.packages;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PackagePickupDTO(
        @NotNull(message = "ID do usuário que entregou é obrigatório")
        UUID deliveredByUserId
) {
}
