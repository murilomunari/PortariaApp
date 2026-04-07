package com.murilo.portariaApp.repository;

import com.murilo.portariaApp.Entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResidentRepository extends JpaRepository<Resident, UUID> {

    boolean existsByEmailAndUnitId(String email, UUID unitId);

    boolean existsByEmailAndUnitIdAndIdNot(String email, UUID unitId, UUID id);

    List<Resident> findByUnitId(UUID unitId);

    List<Resident> findByUnitIdAndActiveTrue(UUID unitId);

    Optional<Resident> findByEmail(String email);

    List<Resident> findByNameContainingIgnoreCase(String name);
}
