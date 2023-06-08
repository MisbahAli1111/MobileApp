package com.qavi.carmaintanence.usermanagement.repositories;

import com.qavi.carmaintanence.usermanagement.entities.otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
}
