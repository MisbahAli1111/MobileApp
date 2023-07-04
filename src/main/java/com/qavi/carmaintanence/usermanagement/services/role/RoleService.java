package com.qavi.carmaintanence.usermanagement.services.role;

import com.qavi.carmaintanence.usermanagement.entities.permission.Permission;
import com.qavi.carmaintanence.usermanagement.entities.role.Role;
import com.qavi.carmaintanence.usermanagement.models.PermissionBitsModel;
import com.qavi.carmaintanence.usermanagement.models.RoleModel;
import com.qavi.carmaintanence.usermanagement.repositories.PermissionRepository;
import com.qavi.carmaintanence.usermanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    public boolean addRole(Role role)
    {
        try {
            roleRepository.save(role);
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean assignPermission(PermissionBitsModel permissionBitsModel,Long roleId) {
        try{
            roleRepository.assignPermission(permissionBitsModel.getPermissionBit(), permissionRepository.findByName(permissionBitsModel.getPermission()).getId(),roleRepository.findById(roleId).get().getId());
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteRoles(Long roleId) {
            try{
                roleRepository.deleteById(roleId);
                return true;
            }
            catch(Exception e)
            {
                System.out.println(e);
                return false;
            }
        }

    public boolean update_role(Long roleId, RoleModel roleModel) {
        Role foundRole = roleRepository.findById(roleId).get();
        if (foundRole != null) {

            try {
                roleModel.setName(foundRole.getName());
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } else {
            System.out.println("Role Not found");
            return false;

        }
    }
}
