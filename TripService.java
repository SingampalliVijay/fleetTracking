package com.fleetTracking.management.services;

import com.fleetTracking.management.dtos.TripRequest;
import com.fleetTracking.management.entities.Driver;
import com.fleetTracking.management.entities.Trip;
import com.fleetTracking.management.entities.Vehicle;
import com.fleetTracking.management.enums.VehicleStatus;
import com.fleetTracking.management.enums.TripStatus;
import com.fleetTracking.management.exceptions.BusinessValidationException;
import com.fleetTracking.management.exceptions.ResourceNotFoundException;
import com.fleetTracking.management.repository.DriverRepo;
import com.fleetTracking.management.repository.VehicleRepo;
import com.fleetTracking.management.repository.TripRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    private final TripRepo tripRepository;
    private final VehicleRepo vehicleRepository;
    private final DriverRepo driverRepository;

    public Trip createTrip(TripRequest request) {

        Vehicle vehicle = vehicleRepository.findById(
            request.vehicleId()
        ).orElseThrow(() ->
            new ResourceNotFoundException(
                "Vehicle not found"
            )
        );

        Driver driver = driverRepository.findById(
            request.driverId()
        ).orElseThrow(() ->
            new ResourceNotFoundException(
                "Driver not found"
            )
        );

        if (vehicle.getStatus() != VehicleStatus.ACTIVE) {
            throw new BusinessValidationException(
                "Vehicle is not active. Cannot create trip."
            );
        }

        if (driver.getAssignedVehicle() == null) {
            throw new BusinessValidationException(
                "Driver is not assigned to any vehicle"
            );
        }

        if (!driver.getAssignedVehicle()
                .getId().equals(vehicle.getId())) {

            throw new BusinessValidationException(
                "Driver is not assigned to this vehicle"
            );
        }

        if (request.startTime().isBefore(LocalDateTime.now())) {
            throw new BusinessValidationException(
                "Trip start time cannot be in the past"
            );
        }

        List<Trip> activeTrips =
            tripRepository.findByDriverIdAndStatus(
                driver.getId(),
                TripStatus.IN_PROGRESS
            );

        boolean driverBusy = activeTrips.stream()
            .anyMatch(trip ->
                trip.getEndTime() == null ||
                trip.getEndTime().isAfter(request.startTime())
            );

        if (driverBusy) {
            throw new BusinessValidationException(
                "Driver already has an active trip"
            );
        }

        Trip trip = new Trip();

        trip.setVehicle(vehicle);
        trip.setDriver(driver);
        trip.setOrigin(request.origin());
        trip.setDestination(request.destination());
        trip.setStartTime(request.startTime());
        trip.setStatus(TripStatus.SCHEDULED);

        return tripRepository.save(trip);
    }

    public Trip startTrip(Long tripId) {

        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Trip not found"
                )
            );

        if (trip.getStatus() != TripStatus.SCHEDULED) {
            throw new BusinessValidationException(
                "Only scheduled trips can be started"
            );
        }

        if (trip.getVehicle().getStatus()
                != VehicleStatus.ACTIVE) {

            throw new BusinessValidationException(
                "Vehicle is not active"
            );
        }

        if (trip.getDriver().getAssignedVehicle() == null ||
            !trip.getDriver().getAssignedVehicle()
                .getId().equals(trip.getVehicle().getId())) {

            throw new BusinessValidationException(
                "Driver is no longer assigned to this vehicle"
            );
        }

        trip.setStatus(TripStatus.IN_PROGRESS);

        return tripRepository.save(trip);
    }

    public Trip completeTrip(Long tripId) {

        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Trip not found"
                )
            );

        if (trip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new BusinessValidationException(
                "Only in-progress trips can be completed"
            );
        }

        trip.setEndTime(LocalDateTime.now());
        trip.setStatus(TripStatus.COMPLETED);

        return tripRepository.save(trip);
    }
}

