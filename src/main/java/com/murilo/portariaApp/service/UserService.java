package com.murilo.portariaApp.service;

import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.dto.user.UserRequestDTO;
import com.murilo.portariaApp.enums.Role;
import com.murilo.portariaApp.exception.UserException;
import com.murilo.portariaApp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
