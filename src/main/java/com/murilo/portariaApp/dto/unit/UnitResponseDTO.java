package com.murilo.portariaApp.dto.unit;

import java.util.UUID;

public record UnitResponseDTO(
        String number,
        String block,
        Integer floor,
        String description
) {
}
