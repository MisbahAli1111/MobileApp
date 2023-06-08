package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.models.VehicleModel;
import com.qavi.carmaintanence.business.repositories.BusinessRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    UserRepository userRepository;
    public boolean addVehicle(Long businessId, VehicleModel vehicleModel)
    {
        try
        {
            Vehicle vehicle=new Vehicle();
            Optional<Business> business = businessRepository.findById(businessId);
            if (business.isPresent()) {
                vehicle.setAssociatedToBusiness(List.of(business.get()));
                Optional<User> owner=userRepository.findById(vehicleModel.getOwnerId());
                if(owner.isPresent())
                {
                    vehicle.setCarOwner(owner.get());
                    vehicle.setMake(vehicleModel.getMake());
                    vehicle.setColor(vehicleModel.getColor());
                    vehicle.setModel(vehicleModel.getModel());
                    vehicle.setYear(vehicleModel.getYear());
                    vehicle.setType(vehicleModel.getType());
                    vehicle.setKilometerDriven(vehicleModel.getKilometerDriven());
                    vehicle.setRegistrationNumber(vehicleModel.getRegistrationNumber());
                    vehicleRepository.save(vehicle);
                    return true;
                }
                else
                {
                    throw new RecordNotFoundException("User not found");
                }
            }
            else{
                throw new RecordNotFoundException("Business not found");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }

    public List<Vehicle> getAllVehiclesOfBusiness(Long businessId)
    {
        return vehicleRepository.findAllByAssociatedToBusinessId(businessId);
    }

    public Optional<Vehicle> getVehicle(Long id)
    {
        return vehicleRepository.findById(id);
    }
}
