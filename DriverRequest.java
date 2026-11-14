package com.fleetTracking.management.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
public record DriverRequest(

    @NotBlank
    @Size(min = 3, max = 100)
    String name,

    @NotBlank
    @Size(min = 5, max = 30)
    String licenceNumber,

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    String phone,

    Long assignedVehicleId

) {}
