package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findAllByAssociatedToBusinessId(Long businessId);

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT u FROM Vehicle v JOIN v.carOwner u WHERE v.registrationNumber = :registrationNumber")
    Optional<User> getUserNameFromRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    @Query("select v.registration_number from vehicle v INNER JOIN vehicle_associated_to_business b  on v.id = b.vehicle_id where b.associated_to_business_id = ?1;")
    List<Map<String,Object>> getRegistrationNumber(@Param("business_id") long businessId);
}
