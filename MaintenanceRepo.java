package com.fleetTracking.management.repository;

import com.fleetTracking.management.entities.Maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaintenanceRepo extends JpaRepository<Maintenance, Long> {
    
    List<Maintenance> findByVehicleId(Long vehicleId);

    List<Maintenance> findByVehicleIdAndCompletedDateIsNull(
        Long vehicleId
    );
}
