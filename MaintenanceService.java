package com.fleetTracking.management.services;

import com.fleetTracking.management.dtos.MaintenanceRequest;
import com.fleetTracking.management.entities.Maintenance;
import com.fleetTracking.management.entities.Vehicle;
import com.fleetTracking.management.enums.VehicleStatus;
import com.fleetTracking.management.exceptions.BusinessValidationException;
import com.fleetTracking.management.exceptions.ResourceNotFoundException;
import com.fleetTracking.management.repository.MaintenanceRepo;
import com.fleetTracking.management.repository.VehicleRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {

    private final MaintenanceRepo maintenanceRepository;
    private final VehicleRepo vehicleRepository;

    public Maintenance scheduleMaintenance(
            MaintenanceRequest request) {

        Vehicle vehicle = vehicleRepository.findById(
            request.vehicleId()
        ).orElseThrow(() ->
            new ResourceNotFoundException(
                "Vehicle not found"
            )
        );

        if (vehicle.getStatus() == VehicleStatus.RETIRED) {
            throw new BusinessValidationException(
                "Cannot schedule maintenance for retired vehicle"
            );
        }

        if (request.cost() == null ||
                request.cost().doubleValue() < 0) {
            throw new BusinessValidationException(
                "Maintenance cost cannot be negative"
            );
        }

        Maintenance maintenance = new Maintenance();

        maintenance.setVehicle(vehicle);
        maintenance.setServiceType(request.serviceType());
        maintenance.setScheduledDate(request.scheduledDate());
        maintenance.setCost(request.cost());
        maintenance.setDescription(request.description());

        return maintenanceRepository.save(maintenance);
    }

    public Maintenance startMaintenance(Long id) {

        Maintenance maintenance =
            maintenanceRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Maintenance record not found"
                    )
                );

        if (maintenance.getCompletedDate() != null) {
            throw new BusinessValidationException(
                "Maintenance already completed"
            );
        }

        Vehicle vehicle = maintenance.getVehicle();

        if (vehicle.getStatus() == VehicleStatus.RETIRED) {
            throw new BusinessValidationException(
                "Cannot start maintenance on retired vehicle"
            );
        }

        if (maintenance.getScheduledDate()
                .isAfter(LocalDate.now())) {
            throw new BusinessValidationException(
                "Maintenance is scheduled for a future date"
            );
        }

        vehicle.setStatus(VehicleStatus.IN_MAINTENANCE);

        vehicleRepository.save(vehicle);

        return maintenance;
    }

    public Maintenance completeMaintenance(Long id) {

        Maintenance maintenance =
            maintenanceRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Maintenance record not found"
                    )
                );

        if (maintenance.getCompletedDate() != null) {
            throw new BusinessValidationException(
                "Maintenance already completed"
            );
        }

        Vehicle vehicle = maintenance.getVehicle();

        if (vehicle.getStatus() != VehicleStatus.IN_MAINTENANCE) {
            throw new BusinessValidationException(
                "Vehicle is not currently under maintenance"
            );
        }

        if (maintenance.getScheduledDate()
                .isAfter(LocalDate.now())) {
            throw new BusinessValidationException(
                "Maintenance is scheduled for a future date"
            );
        }

        maintenance.setCompletedDate(LocalDate.now());

        Maintenance saved =
            maintenanceRepository.save(maintenance);

        boolean pendingMaintenance =
            maintenanceRepository
                .findByVehicleIdAndCompletedDateIsNull(
                    vehicle.getId()
                )
                .stream()
                .anyMatch(m -> !m.getId().equals(id));

        if (!pendingMaintenance) {
            vehicle.setStatus(VehicleStatus.ACTIVE);
            vehicleRepository.save(vehicle);
        }

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Maintenance> getMaintenanceByVehicle(
            Long vehicleId) {

        if (!vehicleRepository.existsById(vehicleId)) {
            throw new ResourceNotFoundException(
                "Vehicle not found"
            );
        }

        return maintenanceRepository.findByVehicleId(vehicleId);
    }

    @Transactional(readOnly = true)
    public Maintenance getMaintenanceById(Long id) {

        return maintenanceRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Maintenance not found"
                )
            );
    }
}
