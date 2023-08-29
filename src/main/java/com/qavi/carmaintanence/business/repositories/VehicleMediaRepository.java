package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.VehicleMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VehicleMediaRepository extends JpaRepository<VehicleMedia,Long> {
    @Query("SELECT vm.id FROM Vehicle v JOIN v.vehicleMedia vm WHERE v.id = :vehicleId")
    List<Long> getVehicleMediaIdsByVehicleId(@Param("vehicleId") Long vehicleId);


    @Query("SELECT NEW com.qavi.carmaintanence.business.entities.VehicleMedia(vm.id, vm.key) FROM VehicleMedia vm WHERE vm.id IN :ids")
    List<VehicleMedia> getVehicleMediaDataByIds(@Param("ids") List<Long> ids);



}
