package com.murilo.portariaApp.controller;

import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.dto.user.UserRequestDTO;
import com.murilo.portariaApp.dto.user.UserResponseDTO;
import com.murilo.portariaApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = userService.create(userRequestDTO);

        UserResponseDTO response = new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll () {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("{name}")
    public ResponseEntity<UserResponseDTO> findByName(@PathVariable String name) {

        return userService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> patchUser(
            @PathVariable UUID id,
            @RequestBody UserResponseDTO request) {

        return ResponseEntity.ok(userService.patchUser(id, request));
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteByName (@PathVariable String name) {
        userService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}