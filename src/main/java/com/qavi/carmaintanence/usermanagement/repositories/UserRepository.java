package com.qavi.carmaintanence.usermanagement.repositories;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query(value = "Select * from users where email=?1", nativeQuery = true)
    User getUser(String email);


    @Query(value = "SELECT COUNT(*) FROM business_employee WHERE business_id = ?1", nativeQuery = true)
    int getEmployeeCount(Long businessId);

    @Query(value = "select * from users where email=?1 and auth_type=?2", nativeQuery = true)
    Optional<User> findByEmail(String email, String authType);

    Optional<User> findById(Long id);


    @Query(value = "SELECT u.id, CONCAT(u.first_name, ' ', u.last_name) as name FROM users u INNER JOIN business_customer b ON u.id = b.customer_id WHERE b.business_id = ?1", nativeQuery = true)
    List<Map<String, Object>> findCustomers(Long businessId);



//    @Query(value = "insert into users(first_name,email_notification_enabled,enabled) VALUES(:name,false,true)",nativeQuery = true)
//    void insertVehicleCustomers(@Param("name") String name);

    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO business_employee (business_id, employee_id) VALUES (:businessId, :userId)", nativeQuery = true)
    void insertEmployeeIntoBusiness(@Param("businessId") Long businessId, @Param("userId") Long userId);


    @Modifying
    @Query(value = "INSERT INTO business_customer(business_id, customer_id) VALUES (:businessId, :userId)", nativeQuery = true)
    void insertCustomerIntoBusiness(@Param("businessId") Long businessId, @Param("userId") Long userId);

}

