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

    @Query("SELECT id FROM Vehicle WHERE registrationNumber = :registrationNumber")
    Optional<Long> getVehicleIdByRegistrationNumber(@Param("registrationNumber") String registrationNumber);


    @Query(value = "select v.registration_number from Vehicle v INNER JOIN vehicle_associated_to_business b  on v.id = b.vehicle_id where b.associated_to_business_id = ?1",nativeQuery = true)
    List<Map<String, Object>> findRegistrationNumberInBusiness(Long businessId);
}
