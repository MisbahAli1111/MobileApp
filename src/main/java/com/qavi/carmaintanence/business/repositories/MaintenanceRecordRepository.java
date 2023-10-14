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

    @Query(value = "WITH current_week_data AS (\n" +
            "  SELECT EXTRACT(DOW FROM CURRENT_DATE) AS current_weekday,\n" +
            "         EXTRACT(WEEK FROM CURRENT_DATE) AS current_week\n" +
            ")\n" +
            "\n" +
            "SELECT\n" +
            "  day_of_week,\n" +
            "  COALESCE(COUNT(mr.maintanence_date_time), 0) AS count\n" +
            "FROM (\n" +
            "  SELECT\n" +
            "    generate_series(0, 6) AS day_of_week\n" +
            ") AS days\n" +
            "LEFT JOIN maintenance_record mr\n" +
            "  ON EXTRACT(DOW FROM mr.maintanence_date_time) = day_of_week\n" +
            "  AND EXTRACT(WEEK FROM mr.maintanence_date_time) = (SELECT current_week FROM current_week_data)\n" +
            "GROUP BY day_of_week\n" +
            "ORDER BY day_of_week;\n", nativeQuery = true)
    List<Object[]> findRecordByYear(Long maintenanceRecordId);


    List<MaintenanceRecord> findAllByBusinessId(Long businessId);




    @Query(value = "SELECT v.car_owner_id FROM vehicle v INNER JOIN maintenance_record m ON m.vehicle_id = v.id WHERE m.id = ?1", nativeQuery = true)
    Long getOwnerId(Long maintenanceRecordId);
}
