
package com.fleetTracking.management.services;

import com.fleetTracking.management.dtos.VehicleOwnerRequest;
import com.fleetTracking.management.entities.VehicleOwner;
import com.fleetTracking.management.exceptions.BusinessValidationException;
import com.fleetTracking.management.exceptions.DuplicateResourceException;
import com.fleetTracking.management.exceptions.ResourceNotFoundException;
import com.fleetTracking.management.repository.VehicleOwnerRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleOwnerService {

    private final VehicleOwnerRepo ownerRepository;

    public VehicleOwner createOwner(VehicleOwnerRequest request) {

        if (ownerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                "Email already exists"
            );
        }

        VehicleOwner owner = new VehicleOwner();

        owner.setName(request.name());
        owner.setContactNumber(request.contactNumber());
        owner.setEmail(request.email());
        owner.setAddress(request.address());

        return ownerRepository.save(owner);
    }

    @Transactional(readOnly = true)
    public VehicleOwner getOwnerById(Long id) {

        return ownerRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Owner not found with ID: " + id
                )
            );
    }

    @Transactional(readOnly = true)
    public List<VehicleOwner> getAllOwners() {

        return ownerRepository.findAll();
    }

    public VehicleOwner updateOwner(
            Long id,
            VehicleOwnerRequest request) {

        VehicleOwner owner = getOwnerById(id);

        ownerRepository.findByEmail(request.email())
            .filter(existing -> !existing.getId().equals(id))
            .ifPresent(existing -> {
                throw new DuplicateResourceException(
                    "Email already belongs to another owner"
                );
            });

        owner.setName(request.name());
        owner.setContactNumber(request.contactNumber());
        owner.setEmail(request.email());
        owner.setAddress(request.address());

        return ownerRepository.save(owner);
    }

    public void deleteOwner(Long id) {

        VehicleOwner owner = getOwnerById(id);

        if (!owner.getVehicles().isEmpty()) {
            throw new BusinessValidationException(
                "Cannot delete owner with associated vehicles"
            );
        }

        ownerRepository.delete(owner);
    }
}
