package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.MaintenanceRecordMedia;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordMediaRepository extends JpaRepository<MaintenanceRecordMedia,Long> {

    @Query("SELECT mrm.id FROM MaintenanceRecord mr JOIN mr.maintenanceRecordMedia mrm WHERE mr.id = :recordId")
    List<Long> getRecordMediaIdsByRecordId(@Param("recordId") Long recordId);


    @Query("SELECT NEW com.qavi.carmaintanence.business.entities.MaintenanceRecordMedia(vm.id, vm.key) FROM MaintenanceRecordMedia vm WHERE vm.id IN :ids")
    List<MaintenanceRecordMedia> getRecordMediaDataByIds(@Param("ids") List<Long> ids);
}
