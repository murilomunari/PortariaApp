package com.murilo.portariaApp.service;

import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.dto.user.UserRequestDTO;
import com.murilo.portariaApp.enums.Role;
import com.murilo.portariaApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public User create(UserRequestDTO request) {
        String name = validateName(request.name());
        String email = validateEmail(request.email());
        String password = validatePassword(request.password());
        Role role = validateRole(request.role());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ja existe um usuario cadastrado com este email.");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
    }


    @Transactional
    public User update(UUID id, UserRequestDTO request) {
        User user = findExistingUser(id);
        String name = validateName(request.name());
        String email = validateEmail(request.email());
        String password = validatePassword(request.password());
        Role role = validateRole(request.role());

        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new IllegalArgumentException("Ja existe outro usuario cadastrado com este email.");
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        if (request.active() != null) {
            user.setActive(request.active());
        }

        return userRepository.save(user);
    }


    @Transactional
    public void delete(UUID id) {
        User user = findExistingUser(id);

        if (Role.ADMIN.equals(user.getRole())) {
            throw new IllegalArgumentException("Nao e permitido deletar usuario ADMIN.");
        }

        userRepository.delete(user);
    }


    public User findById(UUID id) {
        return findExistingUser(id);
    }

    private User findExistingUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado."));
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }

        return name.trim();
    }

    private String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email e obrigatorio.");
        }

        return email.trim();
    }

    private String validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Senha e obrigatoria.");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }

        return password;
    }

    private Role validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role e obrigatoria.");
        }

        return role;
    }
}
