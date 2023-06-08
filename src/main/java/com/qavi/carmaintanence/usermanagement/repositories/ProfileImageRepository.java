package com.qavi.carmaintanence.usermanagement.repositories;

import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage,Long> {
}
