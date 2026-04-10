package com.murilo.portariaApp.service;

import com.murilo.portariaApp.Entity.Package;
import com.murilo.portariaApp.Entity.Resident;
import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.dto.packages.PackageRequestDTO;
import com.murilo.portariaApp.dto.packages.PackageResponseDTO;
import com.murilo.portariaApp.enums.PackageStatus;
import com.murilo.portariaApp.exception.PackageException;
import com.murilo.portariaApp.repository.PackageRepository;
import com.murilo.portariaApp.repository.ResidentRepository;
import com.murilo.portariaApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final ResidentRepository residentRepository;
    private final UserRepository userRepository;

    @Transactional
    public PackageResponseDTO create(PackageRequestDTO dto) {
        Resident resident = residentRepository.findById(dto.residentId())
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado com id " + dto.residentId()));

        User receivedByUser = userRepository.findById(dto.receivedByUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id " + dto.receivedByUserId()));

        var packageEntity = Package.builder()
                .description(dto.description())
                .sender(dto.sender())
                .trackingCode(dto.trackingCode())
                .notes(dto.notes())
                .resident(resident)
                .receivedBy(receivedByUser)
                .status(PackageStatus.RECEIVED)
                .receivedAt(LocalDateTime.now())
                .build();

        return toResponse(packageRepository.save(packageEntity));
    }

    @Transactional(readOnly = true)
    public List<PackageResponseDTO> findAll() {
        return packageRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PackageResponseDTO findById(UUID id) {
        var packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada com id " + id));
        return toResponse(packageEntity);
    }

    @Transactional
    public PackageResponseDTO pickupPackage(UUID id, UUID deliveredByUserId) {
        var packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada com id " + id));

        if (packageEntity.getStatus() == PackageStatus.PICKED_UP) {
            throw new PackageException("Encomenda já foi retirada");
        }

        if (packageEntity.getStatus() == PackageStatus.CANCELED) {
            throw new PackageException("Encomenda foi cancelada e não pode ser retirada");
        }

        User deliveredByUser = userRepository.findById(deliveredByUserId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id " + deliveredByUserId));

        packageEntity.setStatus(PackageStatus.PICKED_UP);
        packageEntity.setPickedUpAt(LocalDateTime.now());
        packageEntity.setDeliveredBy(deliveredByUser);

        return toResponse(packageRepository.save(packageEntity));
    }

    @Transactional
    public PackageResponseDTO cancelPackage(UUID id) {
        var packageEntity = packageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada com id " + id));

        if (packageEntity.getStatus() == PackageStatus.PICKED_UP) {
            throw new PackageException("Encomenda já foi retirada e não pode ser cancelada");
        }

        packageEntity.setStatus(PackageStatus.CANCELED);

        return toResponse(packageRepository.save(packageEntity));
    }

    private PackageResponseDTO toResponse(Package packageEntity) {
        return new PackageResponseDTO(
                packageEntity.getId(),
                packageEntity.getDescription(),
                packageEntity.getSender(),
                packageEntity.getTrackingCode(),
                packageEntity.getReceivedAt(),
                packageEntity.getPickedUpAt(),
                packageEntity.getStatus(),
                packageEntity.getNotes(),
                packageEntity.getResident().getId(),
                packageEntity.getResident().getName(),
                packageEntity.getReceivedBy().getId(),
                packageEntity.getReceivedBy().getName(),
                packageEntity.getDeliveredBy() != null ? packageEntity.getDeliveredBy().getId() : null,
                packageEntity.getDeliveredBy() != null ? packageEntity.getDeliveredBy().getName() : null
        );
    }
}
