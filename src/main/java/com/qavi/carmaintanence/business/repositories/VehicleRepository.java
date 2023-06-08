package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findAllByAssociatedToBusinessId(Long businessId);
}
