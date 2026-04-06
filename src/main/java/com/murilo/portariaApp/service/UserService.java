package com.murilo.portariaApp.service;

import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.dto.user.UserRequestDTO;
import com.murilo.portariaApp.dto.user.UserResponseDTO;
import com.murilo.portariaApp.enums.Role;
import com.murilo.portariaApp.exception.UserException;
import com.murilo.portariaApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create (UserRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new UserException("Email ja cadastrado!");
        }

        User users = new User();

        users.setName(request.name());
        users.setEmail(request.email());
        users.setPassword(request.password());
        users.setRole(request.role());
        users.setActive(true);

        return userRepository.save(users);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findByName(String name) {
        return userRepository.findByName(name)
                .map(user -> new UserResponseDTO(
                        user.getName(),
                        user.getEmail()
                ));
    }

    @Transactional
    public UserResponseDTO patchUser(UUID id, UserResponseDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        if (request.name() != null) {
            user.setName(request.name());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }

        userRepository.save(user);

        return new UserResponseDTO(
                user.getName(),
                user.getEmail()
        );
    }

}
