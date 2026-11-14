package com.fleetTracking.management.dtos;


import com.fleetTracking.management.enums.VehicleStatus;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record VehicleRequest(

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9 -]{5,15}$")
    String licencePlate,

    @NotBlank
    @Size(min = 2, max = 50)
    String make,

    @NotBlank
    @Size(min = 1, max = 50)
    String model,

    @NotNull
    @Min(1950)
    @Max(2100)
    Integer manufactureYear,

    @NotNull
    VehicleStatus status,

    @NotNull
    Long ownerId

) {}
