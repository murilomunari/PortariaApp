package com.murilo.portariaApp.controller;

import com.murilo.portariaApp.dto.packages.PackagePickupDTO;
import com.murilo.portariaApp.dto.packages.PackageRequestDTO;
import com.murilo.portariaApp.dto.packages.PackageResponseDTO;
import com.murilo.portariaApp.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/package")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping
    public ResponseEntity<PackageResponseDTO> create(@Valid @RequestBody PackageRequestDTO request) {
        var response = packageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PackageResponseDTO>> findAll() {
        return ResponseEntity.ok(packageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(packageService.findById(id));
    }

    @PatchMapping("/{id}/pickup")
    public ResponseEntity<PackageResponseDTO> pickupPackage(
            @PathVariable UUID id,
            @Valid @RequestBody PackagePickupDTO request) {
        return ResponseEntity.ok(packageService.pickupPackage(id, request.deliveredByUserId()));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PackageResponseDTO> cancelPackage(@PathVariable UUID id) {
        return ResponseEntity.ok(packageService.cancelPackage(id));
    }
}
