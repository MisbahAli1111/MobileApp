package com.qavi.carmaintanence.usermanagement.repositories;

import com.qavi.carmaintanence.usermanagement.entities.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}
