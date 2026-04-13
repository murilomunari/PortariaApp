package com.murilo.portariaApp.service;

import com.murilo.portariaApp.Entity.Resident;
import com.murilo.portariaApp.Entity.Unit;
import com.murilo.portariaApp.dto.resident.ResidentRequestDTO;
import com.murilo.portariaApp.dto.resident.ResidentResponseDTO;
import com.murilo.portariaApp.exception.ResidentException;
import com.murilo.portariaApp.repository.ResidentRepository;
import com.murilo.portariaApp.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResidentService {

    private final ResidentRepository residenteRepository;
    private final UnitRepository unitRepository;

    @Transactional
    public ResidentResponseDTO create(ResidentRequestDTO dto) {
        Unit unit = unitRepository.findById(dto.unitId())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com id " + dto.unitId()));

        if (residenteRepository.existsByEmailAndUnitId(dto.email(), dto.unitId())) {
            throw new ResidentException("Já existe um morador com o email " + dto.email() + " na unidade " + unit.getNumber());
        }

        var resident = Resident.builder()
                .name(dto.name())
                .phone(dto.phone())
                .email(dto.email())
                .unit(unit)
                .active(true)
                .build();

        return toResponse(residenteRepository.save(resident));
    }

    @Transactional(readOnly = true)
    public List<ResidentResponseDTO> findAll() {
        return residenteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ResidentResponseDTO findById(UUID id) {
        var resident = residenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado com id " + id));
        return toResponse(resident);
    }

    @Transactional(readOnly = true)
    public List<ResidentResponseDTO> findByUnit(UUID unitId) {
        return residenteRepository.findByUnitIdAndActiveTrue(unitId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResidentResponseDTO> findByName(String name) {
        return residenteRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ResidentResponseDTO update(UUID id, ResidentRequestDTO dto) {
        var resident = residenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado com id " + id));

        Unit unit = unitRepository.findById(dto.unitId())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com id " + dto.unitId()));

        if (residenteRepository.existsByEmailAndUnitIdAndIdNot(dto.email(), dto.unitId(), id)) {
            throw new ResidentException("Já existe um morador com o email " + dto.email() + " na unidade " + unit.getNumber());
        }

        resident.setName(dto.name());
        resident.setPhone(dto.phone());
        resident.setEmail(dto.email());
        resident.setUnit(unit);

        return toResponse(residenteRepository.save(resident));
    }

    @Transactional
    public void deactivate(UUID id) {
        var resident = residenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado com id " + id));

        resident.setActive(false);
        residenteRepository.save(resident);
    }

    private ResidentResponseDTO toResponse(Resident resident) {
        return new ResidentResponseDTO(
                resident.getName(),
                resident.getPhone(),
                resident.getEmail(),
                resident.getActive(),
                resident.getUnit().getId(),
                resident.getUnit().getNumber()
        );
    }
}
