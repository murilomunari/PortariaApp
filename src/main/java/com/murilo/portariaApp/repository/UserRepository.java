package com.murilo.portariaApp.repository;

import com.murilo.portariaApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> deleteByName(String name);
}
