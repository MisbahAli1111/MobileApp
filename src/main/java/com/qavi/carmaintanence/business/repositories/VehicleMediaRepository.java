package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.VehicleMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleMediaRepository extends JpaRepository<VehicleMedia,Long> {
}
