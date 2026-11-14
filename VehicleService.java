package com.fleetTracking.management.services;



import com.fleetTracking.management.dtos.VehicleRequest;
import com.fleetTracking.management.entities.Vehicle;
import com.fleetTracking.management.entities.VehicleOwner;
import com.fleetTracking.management.exceptions.*;
import com.fleetTracking.management.repository.VehicleOwnerRepo;
import com.fleetTracking.management.repository.VehicleRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {

    private final VehicleRepo vehicleRepository;
    private final VehicleOwnerRepo ownerRepository;

    public Vehicle createVehicle(VehicleRequest request) {

        if (vehicleRepository.existsByLicencePlate(
                request.licencePlate())) {

            throw new DuplicateResourceException(
                "Vehicle registration already exists"
            );
        }

        VehicleOwner owner = ownerRepository.findById(
            request.ownerId()
        ).orElseThrow(() ->
            new ResourceNotFoundException(
                "Owner not found"
            )
        );

        Vehicle vehicle = new Vehicle();

        vehicle.setLicencePlate(request.licencePlate());
        vehicle.setMake(request.make());
        vehicle.setModel(request.model());
        vehicle.setManufactureYear(request.manufactureYear());
        vehicle.setStatus(request.status());
        vehicle.setOwner(owner);

        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {

        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByOwner(Long ownerId) {

        if (!ownerRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException(
                "Owner not found"
            );
        }

        return vehicleRepository.findByOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Long id) {

        return vehicleRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Vehicle not found: " + id
                )
            );
    }
}

