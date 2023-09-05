package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord,Long> {
    @Query("SELECT u.email FROM User u WHERE u.id IN ( SELECT v.carOwner.id FROM MaintenanceRecord  m INNER JOIN Vehicle v ON m.vehicle = v.id  WHERE DATE(m.serviceDue) = CURRENT_DATE + 40)")
    List<String> getServiceDue();
}
