package com.fleetTracking.management.services;

import com.fleetTracking.management.dtos.DriverRequest;
import com.fleetTracking.management.entities.Driver;
import com.fleetTracking.management.entities.Vehicle;
import com.fleetTracking.management.enums.VehicleStatus;
import com.fleetTracking.management.exceptions.*;
import com.fleetTracking.management.repository.DriverRepo;
import com.fleetTracking.management.repository.VehicleRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {

    private final DriverRepo driverRepository;
    private final VehicleRepo vehicleRepository;

    public Driver createDriver(DriverRequest request) {

        if (driverRepository.existsByLicenceNumber(
                request.licenceNumber())) {

            throw new DuplicateResourceException(
                "Driver licence already exists"
            );
        }

        Driver driver = new Driver();

        driver.setName(request.name());
        driver.setLicenceNumber(request.licenceNumber());
        driver.setPhone(request.phone());

        if (request.assignedVehicleId() != null) {

            Vehicle vehicle = vehicleRepository.findById(
                request.assignedVehicleId()
            ).orElseThrow(() ->
                new ResourceNotFoundException(
                    "Vehicle not found"
                )
            );

            if (vehicle.getStatus() == VehicleStatus.RETIRED) {
                throw new BusinessValidationException(
                    "Cannot assign driver to retired vehicle"
                );
            }

            driver.setAssignedVehicle(vehicle);
        }

        return driverRepository.save(driver);
    }
}

