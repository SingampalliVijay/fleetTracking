package com.fleetTracking.management.repository;

import com.fleetTracking.management.entities.Vehicle;
import com.fleetTracking.management.enums.VehicleStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    
    Optional<Vehicle> findByLicencePlate(String licencePlate);

    boolean existsByLicencePlate(String licencePlate);

    List<Vehicle> findByOwnerId(Long ownerId);

    List<Vehicle> findByStatus(VehicleStatus status);

    long countByOwnerId(Long ownerId);
}
