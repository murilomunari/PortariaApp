package com.murilo.portariaApp.repository;

import com.murilo.portariaApp.Entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

    boolean existsByNumber(String number);

    boolean existsByNumberAndIdNot(String number, UUID id);

    List<Unit> findByNumber(String number);
}
