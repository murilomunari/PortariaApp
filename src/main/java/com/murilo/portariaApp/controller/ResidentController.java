package com.murilo.portariaApp.controller;

import com.murilo.portariaApp.dto.resident.ResidentRequestDTO;
import com.murilo.portariaApp.dto.resident.ResidentResponseDTO;
import com.murilo.portariaApp.service.ResidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/resident")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @PostMapping
    public ResponseEntity<ResidentResponseDTO> create(@Valid @RequestBody ResidentRequestDTO request) {
        var response = residentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResidentResponseDTO>> findAll() {
        return ResponseEntity.ok(residentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(residentService.findById(id));
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<ResidentResponseDTO>> findByUnit(@PathVariable UUID unitId) {
        return ResponseEntity.ok(residentService.findByUnit(unitId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResidentResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(residentService.findByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ResidentRequestDTO request) {
        return ResponseEntity.ok(residentService.update(id, request));
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        residentService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
