package com.fleetTracking.management.controller;

import com.fleetTracking.management.dtos.VehicleRequest;
import com.fleetTracking.management.entities.Vehicle;
import com.fleetTracking.management.services.VehicleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // Create Vehicle
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(
            @RequestBody VehicleRequest request) {

        return ResponseEntity.ok(
            vehicleService.createVehicle(request)
        );
    }

    // Get Vehicle By ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            vehicleService.getVehicleById(id)
        );
    }

    // Get All Vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {

        return ResponseEntity.ok(
            vehicleService.getAllVehicles()
        );
    }

    // Get Vehicles By Owner ID
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByOwner(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(
            vehicleService.getVehiclesByOwner(ownerId)
        );
    }
}
