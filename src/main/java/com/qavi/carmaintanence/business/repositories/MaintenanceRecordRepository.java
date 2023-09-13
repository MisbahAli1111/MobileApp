package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord,Long> {
    @Query("SELECT m FROM MaintenanceRecord m WHERE m.serviceDue >= :startDateTime AND m.serviceDue <= :endDateTime")
    List<MaintenanceRecord> findByServiceDueBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    List<MaintenanceRecord> findAllByBusinessId(Long businessId);

    @Query(value = "SELECT v.car_owner_id FROM vehicle v INNER JOIN maintenance_record m ON m.vehicle_id = v.id WHERE m.id = ?1", nativeQuery = true)
    Long getOwnerId(Long maintenanceRecordId);
}
