package com.murilo.portariaApp.dto.packages;

import com.murilo.portariaApp.enums.PackageStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PackageResponseDTO(
        UUID id,
        String description,
        String sender,
        String trackingCode,
        LocalDateTime receivedAt,
        LocalDateTime pickedUpAt,
        PackageStatus status,
        String notes,
        UUID residentId,
        String residentName,
        UUID receivedByUserId,
        String receivedByUserName,
        UUID deliveredByUserId,
        String deliveredByUserName
) {
}
