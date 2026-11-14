package com.fleetTracking.management.controller;



import com.fleetTracking.management.dtos.DriverRequest;
import com.fleetTracking.management.entities.Driver;
import com.fleetTracking.management.services.DriverService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    // Create Driver
    @PostMapping
    public ResponseEntity<Driver> createDriver(
            @RequestBody DriverRequest request) {

        return ResponseEntity.ok(
            driverService.createDriver(request)
        );
    }
}
