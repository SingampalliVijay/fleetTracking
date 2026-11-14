package com.fleetTracking.management.controller;

import com.fleetTracking.management.dtos.VehicleOwnerRequest;
import com.fleetTracking.management.entities.VehicleOwner;
import com.fleetTracking.management.services.VehicleOwnerService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class VehicleOwnerController {

    private final VehicleOwnerService ownerService;

    @PostMapping
    public ResponseEntity<VehicleOwner> createOwner(
            @RequestBody VehicleOwnerRequest request) {

        return ResponseEntity.ok(
            ownerService.createOwner(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleOwner> getOwnerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            ownerService.getOwnerById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<VehicleOwner>> getAllOwners() {

        return ResponseEntity.ok(
            ownerService.getAllOwners()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleOwner> updateOwner(
            @PathVariable Long id,
            @RequestBody VehicleOwnerRequest request) {

        return ResponseEntity.ok(
            ownerService.updateOwner(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(
            @PathVariable Long id) {

        ownerService.deleteOwner(id);

        return ResponseEntity.noContent().build();
    }
}
