package com.murilo.portariaApp.controller;

import com.murilo.portariaApp.dto.unit.UnitRequestDTO;
import com.murilo.portariaApp.dto.unit.UnitResponseDTO;
import com.murilo.portariaApp.service.UnitService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping
    public ResponseEntity<UnitResponseDTO> create(@Valid @RequestBody UnitRequestDTO request) {
        var response = unitService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UnitResponseDTO>> findAll() {
        return ResponseEntity.ok(unitService.findAll());
    }

    @GetMapping("/{number}")
    public ResponseEntity<List<UnitResponseDTO>> findByNumber(@PathVariable String number) {
        return ResponseEntity.ok(unitService.findByNumber(number));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody UnitRequestDTO request) {
        return ResponseEntity.ok(unitService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        unitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
