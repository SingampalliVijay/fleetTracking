package com.fleetTracking.management.repository;

import com.fleetTracking.management.entities.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VehicleOwnerRepo extends JpaRepository<VehicleOwner, Long> {
    
    Optional<VehicleOwner> findByEmail(String email);

    boolean existsByEmail(String email);
}

