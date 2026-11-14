package com.fleetTracking.management.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

public record VehicleOwnerRequest(

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100)
    String name,

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    String contactNumber,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(max = 255)
    String address

) {}
