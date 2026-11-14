package com.fleetTracking.management.entities;

import com.fleetTracking.management.enums.VehicleStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(
    name = "vehicles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "licence_plate")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Licence plate is required")
    @Pattern(
        regexp = "^[A-Z0-9 -]{5,15}$",
        message = "Invalid licence plate"
    )
    @Column(name = "licence_plate",
            nullable = false,
            unique = true)
    private String licencePlate;

    @NotBlank(message = "Make is required")
    @Size(min = 2, max = 50)
    private String make;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 50)
    private String model;

    @NotNull(message = "Manufacture year is required")
    @Min(1950)
    @Max(2100)
    private Integer manufactureYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status = VehicleStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull(message = "Vehicle owner is required")
    private VehicleOwner owner;
}