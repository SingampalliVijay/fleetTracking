package com.fleetTracking.management.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record TripRequest(

    @NotNull
    Long vehicleId,

    @NotNull
    Long driverId,

    @NotBlank
    @Size(max = 150)
    String origin,

    @NotBlank
    @Size(max = 150)
    String destination,

    @NotNull
    LocalDateTime startTime

) {}