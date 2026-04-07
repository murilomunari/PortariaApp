package com.murilo.portariaApp.service;

import com.murilo.portariaApp.Entity.Unit;
import com.murilo.portariaApp.dto.unit.UnitRequestDTO;
import com.murilo.portariaApp.dto.unit.UnitResponseDTO;
import com.murilo.portariaApp.exception.UnitException;
import com.murilo.portariaApp.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    @Transactional
    public UnitResponseDTO create(UnitRequestDTO dto) {
        if (unitRepository.existsByNumber(dto.number())) {
            throw new UnitException("Já existe uma unidade com o número de apartamento " + dto.number());
        }

        var unit = Unit.builder()
                .number(dto.number())
                .block(dto.block())
                .floor(dto.floor())
                .description(dto.description())
                .build();

        var saved = unitRepository.save(unit);
        return toResponse(saved);
    }

    public List<UnitResponseDTO> findAll() {
        return unitRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UnitResponseDTO update(UUID id, UnitRequestDTO dto) {
        var unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com id " + id));

        if (unitRepository.existsByNumberAndIdNot(dto.number(), id)) {
            throw new UnitException("Já existe uma unidade com o número de apartamento " + dto.number());
        }

        unit.setNumber(dto.number());
        unit.setBlock(dto.block());
        unit.setFloor(dto.floor());
        unit.setDescription(dto.description());

        return toResponse(unitRepository.save(unit));
    }

    @Transactional
    public void delete(UUID id) {
        if (!unitRepository.existsById(id)) {
            throw new EntityNotFoundException("Unidade não encontrada com id " + id);
        }
        unitRepository.deleteById(id);
    }

    public List<UnitResponseDTO> findByNumber(String number) {
        return unitRepository.findByNumber(number).stream()
                .map(this::toResponse)
                .toList();
    }

    private UnitResponseDTO toResponse(Unit unit) {
        return new UnitResponseDTO(
                unit.getNumber(),
                unit.getBlock(),
                unit.getFloor(),
                unit.getDescription()
        );
    }
}
