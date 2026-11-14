package com.fleetTracking.management.entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicle_owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Owner name is required")
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Contact number is required")
    @Pattern(
        regexp = "^\\+?[0-9]{10,15}$",
        message = "Invalid contact number"
    )
    @Column(nullable = false)
    private String contactNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Address is required")
    @Size(max = 255)
    @Column(nullable = false)
    private String address;

    @OneToMany(
        mappedBy = "owner",
        cascade = CascadeType.ALL
    )
    private List<Vehicle> vehicles = new ArrayList<>();
}
