package com.fleetTracking.management.controller;

import com.fleetTracking.management.dtos.MaintenanceRequest;
import com.fleetTracking.management.entities.Maintenance;
import com.fleetTracking.management.services.MaintenanceService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    // Schedule Maintenance
    @PostMapping
    public ResponseEntity<Maintenance> scheduleMaintenance(
            @RequestBody MaintenanceRequest request) {

        return ResponseEntity.ok(
            maintenanceService.scheduleMaintenance(request)
        );
    }

    // Start Maintenance
    @PutMapping("/{id}/start")
    public ResponseEntity<Maintenance> startMaintenance(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            maintenanceService.startMaintenance(id)
        );
    }

    // Complete Maintenance
    @PutMapping("/{id}/complete")
    public ResponseEntity<Maintenance> completeMaintenance(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            maintenanceService.completeMaintenance(id)
        );
    }

    // Get Maintenance By ID
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            maintenanceService.getMaintenanceById(id)
        );
    }

    // Get Maintenance By Vehicle ID
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Maintenance>> getMaintenanceByVehicle(
            @PathVariable Long vehicleId) {

        return ResponseEntity.ok(
            maintenanceService.getMaintenanceByVehicle(vehicleId)
        );
    }
}

