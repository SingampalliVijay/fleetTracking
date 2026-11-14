package com.fleetTracking.management.repository;

import com.fleetTracking.management.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DriverRepo extends JpaRepository<Driver, Long> {
    
    Optional<Driver> findByLicenceNumber(String licenceNumber);

    boolean existsByLicenceNumber(String licenceNumber);

    List<Driver> findByAssignedVehicleId(Long vehicleId);

    List<Driver> findByAssignedVehicleIsNull();
}
