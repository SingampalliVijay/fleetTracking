package com.fleetTracking.management.repository;

import com.fleetTracking.management.entities.Trip;
import com.fleetTracking.management.enums.TripStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepo extends JpaRepository<Trip, Long> {
    
     List<Trip> findByVehicleId(Long vehicleId);

    List<Trip> findByDriverId(Long driverId);

    List<Trip> findByStatus(TripStatus status);

    List<Trip> findByVehicleIdAndStatus(
        Long vehicleId,
        TripStatus status
    );

    List<Trip> findByDriverIdAndStatus(
        Long driverId,
        TripStatus status
    );
}

