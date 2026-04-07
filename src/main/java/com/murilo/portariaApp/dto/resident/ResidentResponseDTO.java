package com.murilo.portariaApp.dto.resident;

import java.util.UUID;

public record ResidentResponseDTO(
        String name,
        String phone,
        String email,
        Boolean active,
        UUID unitId,
        String unitNumber
) {
}
