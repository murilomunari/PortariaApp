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
                user.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}