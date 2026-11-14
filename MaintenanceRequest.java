package com.fleetTracking.management.dtos;


import com.fleetTracking.management.enums.ServiceType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceRequest(

    @NotNull
    Long vehicleId,

    @NotNull
    ServiceType serviceType,

    @NotNull
    LocalDate scheduledDate,

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 10, fraction = 2)
    BigDecimal cost,

    @Size(max = 500)
    String description

) {}
