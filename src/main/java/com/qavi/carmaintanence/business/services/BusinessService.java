package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.repositories.BusinessRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.entities.role.Role;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.repositories.RoleRepository;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    public boolean addBusiness(String id, Business business)
    {
        try
        {
            Optional<User> owner = userRepository.findById(Long.valueOf(id));
            if (owner.isPresent()) {
                Role role=roleRepository.searchByName("ROLE_OWNER");
                if(!owner.get().getRole().stream().toList().contains(role))
                {
                    System.out.println(owner.get().getRole());
                    throw new RuntimeException("User is not registered as owner");
                }
                else {
                    business.setOwner(owner.get());
                }
                business.setEnabled(true);
               // business.setBusinessRegisteredAt(LocalDateTime.now());
                businessRepository.save(business);
                return true;
            } else {
                throw new RecordNotFoundException("User doesn't exists");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public List<Business> getMyBusinesses(String id) {
        List<Business> fetchedBusiness=businessRepository.findAllByOwnerId(Long.valueOf(id));
        return fetchedBusiness;
    }
    public List<Business> getAllBusiness() {
        List <Business> businesses=businessRepository.findAll();
        return businesses;
    }


    public boolean editBusiness(Business business, Long id) {
        Business foundBusinessRecord =businessRepository.findById(id).get();
        //Service
        if(Objects.nonNull(foundBusinessRecord.getBusinessCity()) &&
                !"".equals(foundBusinessRecord.getBusinessCity()))
        {
            foundBusinessRecord.setBusinessCity(foundBusinessRecord.getBusinessCity());
        }

        if(Objects.nonNull(foundBusinessRecord.getBusinessAddress()) &&
                !"".equals(foundBusinessRecord.getBusinessAddress()))
        {
            foundBusinessRecord.setBusinessAddress(foundBusinessRecord.getBusinessAddress());
        }

        if(Objects.nonNull(foundBusinessRecord.getBusinessCountry()) &&
                !"".equals(foundBusinessRecord.getBusinessCountry()))
        {
            foundBusinessRecord.setBusinessCountry(foundBusinessRecord.getBusinessCountry());
        }

        if(Objects.nonNull(foundBusinessRecord.getBusinessEmail()) &&
                !"".equals(foundBusinessRecord.getBusinessEmail()))
        {
            foundBusinessRecord.setBusinessEmail(foundBusinessRecord.getBusinessEmail());
        }

        if(Objects.nonNull(foundBusinessRecord.getBusinessName()) &&
                !"".equals(foundBusinessRecord.getBusinessName()))
        {
            foundBusinessRecord.setBusinessName(foundBusinessRecord.getBusinessName());
        }
        if(Objects.nonNull(foundBusinessRecord.getBusinessPhoneNumber()) &&
                !"".equals(foundBusinessRecord.getBusinessPhoneNumber()))
        {
            foundBusinessRecord.setBusinessPhoneNumber(foundBusinessRecord.getBusinessPhoneNumber());
        }

        if(foundBusinessRecord != null)
        {
            businessRepository.save(foundBusinessRecord);
            return true;
        }
        else {
            return false;
        }


    }

    public boolean deleteBusiness(Long id) {
        try {
            Optional<Business> business = businessRepository.findById(id);
            if (business.isPresent()) {
                businessRepository.deleteById(id);
                return true;
            } else {
                throw new RecordNotFoundException("Record not found");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
