package com.murilo.portariaApp.dto.packages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PackageRequestDTO(
        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 150)
        String description,

        @NotBlank(message = "Remetente é obrigatório")
        @Size(max = 150)
        String sender,

        @NotNull(message = "ID do morador é obrigatório")
        UUID residentId,

        @NotNull(message = "ID do usuário que recebeu é obrigatório")
        UUID receivedByUserId,

        @Size(max = 100)
        String trackingCode,

        @Size(max = 255)
        String notes
) {
}
