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

    @Query(value = "WITH all_months AS (\n" +
            "  SELECT 1 AS month_number\n" +
            "  UNION SELECT 2\n" +
            "  UNION SELECT 3\n" +
            "  UNION SELECT 4\n" +
            "  UNION SELECT 5\n" +
            "  UNION SELECT 6\n" +
            "  UNION SELECT 7\n" +
            "  UNION SELECT 8\n" +
            "  UNION SELECT 9\n" +
            "  UNION SELECT 10\n" +
            "  UNION SELECT 11\n" +
            "  UNION SELECT 12\n" +
            ")\n" +
            "\n" +
            "SELECT all_months.month_number AS month_count, COALESCE(COUNT(mr.maintanence_date_time), 0) AS count\n" +
            "FROM all_months\n" +
            "LEFT JOIN maintenance_record mr ON all_months.month_number = EXTRACT(MONTH FROM mr.maintanence_date_time)\n" +
            "GROUP BY all_months.month_number\n" +
            "ORDER BY all_months.month_number;\n;\n", nativeQuery = true)
    List<Object[]> findRecordByYear(Long maintenanceRecordId);

    @Query(value = "WITH all_days AS (\n" +
            "  SELECT generate_series(1, 31) AS day_number  -- Adjust the range (1 to 31) as needed for the month\n" +
            ")\n" +
            "\n" +
            "SELECT all_days.day_number AS day_count, COALESCE(COUNT(mr.maintanence_date_time), 0) AS count\n" +
            "FROM all_days\n" +
            "LEFT JOIN maintenance_record mr ON all_days.day_number = EXTRACT(DAY FROM mr.maintanence_date_time)\n" +
            "GROUP BY all_days.day_number\n" +
            "ORDER BY all_days.day_number;\n", nativeQuery = true)
    List<Object[]> findRecordByMonth(Long maintenanceRecordId);

    List<MaintenanceRecord> findAllByBusinessId(Long businessId);

    @Query(value = "WITH all_hours AS (\n" +
            "  SELECT generate_series(0, 23) AS hour_number\n" +
            ")\n" +
            "\n" +
            "SELECT all_hours.hour_number AS hour_count, COALESCE(COUNT(mr.maintanence_date_time), 0) AS count\n" +
            "FROM all_hours\n" +
            "LEFT JOIN maintenance_record mr ON all_hours.hour_number = EXTRACT(HOUR FROM mr.maintanence_date_time)\n" +
            "GROUP BY all_hours.hour_number\n" +
            "ORDER BY all_hours.hour_number;\n", nativeQuery = true)
    List<Object[]> findRecordByDay(Long maintenanceRecordId);


    @Query(value = "SELECT v.car_owner_id FROM vehicle v INNER JOIN maintenance_record m ON m.vehicle_id = v.id WHERE m.id = ?1", nativeQuery = true)
    Long getOwnerId(Long maintenanceRecordId);
}
