package com.qavi.carmaintanence.usermanagement.services.user;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.repositories.BusinessRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.constants.UserConstants;
import com.qavi.carmaintanence.usermanagement.entities.permission.PermissionAssigned;
import com.qavi.carmaintanence.usermanagement.entities.role.Role;
import com.qavi.carmaintanence.usermanagement.entities.user.PasswordUpdate;
import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.RoleModel;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;
import com.qavi.carmaintanence.usermanagement.repositories.ProfileImageRepository;
import com.qavi.carmaintanence.usermanagement.repositories.RoleRepository;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import com.qavi.carmaintanence.usermanagement.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;



    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messages;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
        User user = userRepository.getUser(email);
        Collection<Role> roles = user.getRole();
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>() {
        };
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getPermissionAssigned().toString()));
        }
        CustomUserDetails customUserDetail = new CustomUserDetails(user);
        customUserDetail.setUser(user);
        customUserDetail.setAuthorities(authorities);
        return customUserDetail;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPermissions(roles));
    }

    public List<String> getPermissions(Collection<Role> roles) {

        List<String> permissions = new ArrayList<>();
        for (Role role : roles) {
            Collection<PermissionAssigned> permissionsAssignedToRole = role.getPermissionAssigned();
            for (PermissionAssigned permissionAssigned : permissionsAssignedToRole) {
                if (!permissions.contains(permissionAssigned.getPermission().getName())) {
                    permissions.add(permissionAssigned.getPermission().getName() + " " + permissionAssigned.getPermissionBits());
                }
            }
        }
        return permissions;
    }

    public List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    //GET ONE USER
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    //GET ALL USER
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll().stream().collect(Collectors.toList());
        return users;
    }

    //Create User
    public boolean createUser(User user,String type) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRegisteredAt(LocalDateTime.now());
            user.setAuthType(UserConstants.LOCAL);
            user.setEnabled(false);
            if(type.equalsIgnoreCase("employee")){
                user.setRole(Set.of(roleRepository.searchByName("ROLE_EMPLOYEE"),roleRepository.searchByName("ROLE_CUSTOMER")));
            }
            else if(type.equalsIgnoreCase("owner"))
            {
                user.setRole(Set.of(roleRepository.searchByName("ROLE_OWNER"),roleRepository.searchByName("ROLE_CUSTOMER")));
            }
            else {
                user.setRole(Set.of(roleRepository.searchByName("ROLE_CUSTOMER")));
            }
            user.setEmailNotificationEnabled(true);
            userRepository.save(user);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean createEmployee(User user, String type, String businessId) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRegisteredAt(LocalDateTime.now());
            user.setAuthType(UserConstants.LOCAL);
            user.setEnabled(false);

            if (type.equalsIgnoreCase("employee")) {
                user.setRole(Set.of(roleRepository.searchByName("ROLE_EMPLOYEE"), roleRepository.searchByName("ROLE_CUSTOMER")));
            }

            user.setEmailNotificationEnabled(true);

            User savedUser = userRepository.save(user);

            Long userId = savedUser.getId();

            Long businessIdLong = Long.parseLong(businessId);

            userRepository.insertEmployeeIntoBusiness(businessIdLong, userId);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    //UpdateUser
    public Boolean updateUser(UserDataModel userDataModel, Long id) {
        try {
            User user = userRepository.findById(id).get();
            user.setFirstName(userDataModel.getFirstName());
            user.setLastName(userDataModel.getLastName());
            user.setEmail(userDataModel.getEmail());
            user.setCountryCode(userDataModel.getCountryCode());
            user.setCnicNumber(userDataModel.getCnicNumber());
            user.setPhoneNumber(userDataModel.getPhone_number());
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }


    public Optional<User> findUserByEmail(String email, String authType) {
        return userRepository.findByEmail(email, authType);
    }

    public Boolean assignRole(String email, RoleModel role) {
        Role newRole = roleRepository.searchByName(role.getName());
        if (newRole == null) {
            throw new RecordNotFoundException("Role does not exist");
        }
        User user = userRepository.findByEmail(email).get();
        user.getRole().add(newRole);
        userRepository.save(user);
        return true;
    }


    public void saveProfileImage(Long profileImgId, Long appUserId) {
        ProfileImage savedImg = profileImageRepository.findById(profileImgId).get();
        User user= getUser(appUserId);
        user.setProfileImage(savedImg);
        userRepository.save(user);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }






    public boolean updatePassword(PasswordUpdate passwordUpdate, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null && passwordEncoder.matches(passwordUpdate.getOldPassword(), user.getPassword())) {
            String newEncodedPassword = passwordEncoder.encode(passwordUpdate.getNewPassword());
            user.setPassword(newEncodedPassword);
            userRepository.save(user);
            return true;
        }

        return false;
    }



}
