package com.murilo.portariaApp.repository;

import com.murilo.portariaApp.Entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PackageRepository extends JpaRepository<Package, UUID> {
}
