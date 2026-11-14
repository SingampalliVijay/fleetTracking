package com.fleetTracking.management.controller;

import com.fleetTracking.management.dtos.TripRequest;
import com.fleetTracking.management.entities.Trip;
import com.fleetTracking.management.services.TripService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    // Create Trip
    @PostMapping
    public ResponseEntity<Trip> createTrip(
            @RequestBody TripRequest request) {

        return ResponseEntity.ok(
            tripService.createTrip(request)
        );
    }

    // Start Trip
    @PutMapping("/{tripId}/start")
    public ResponseEntity<Trip> startTrip(
            @PathVariable Long tripId) {

        return ResponseEntity.ok(
            tripService.startTrip(tripId)
        );
    }

    // Complete Trip
    @PutMapping("/{tripId}/complete")
    public ResponseEntity<Trip> completeTrip(
            @PathVariable Long tripId) {

        return ResponseEntity.ok(
            tripService.completeTrip(tripId)
        );
    }
}

