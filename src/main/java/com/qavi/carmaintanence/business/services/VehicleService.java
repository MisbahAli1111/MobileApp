package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.models.VehicleModel;
import com.qavi.carmaintanence.business.repositories.BusinessRepository;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    VehicleMediaRepository vehicleMediaRepository;

    public Long addVehicle(Long businessId, VehicleModel vehicleModel) {
        try {
            Vehicle vehicle = new Vehicle();
            Optional<Business> business = businessRepository.findById(businessId);
            if (business.isPresent()) {
                vehicle.setAssociatedToBusiness(List.of(business.get()));

                Optional<User> owner = userRepository.findById(vehicleModel.getOwnerId());
                if (owner.isPresent()) {
                    vehicle.setCarOwner(owner.get());
                    vehicle.setMake(vehicleModel.getMake());
                    vehicle.setColor(vehicleModel.getColor());
                    vehicle.setModel(vehicleModel.getModel());
                    vehicle.setYear(vehicleModel.getYear());
                    vehicle.setType(vehicleModel.getType());
                    vehicle.setCustomerType(vehicleModel.getCustomerType());
                    vehicle.setParentCompany(vehicleModel.getParentCompany());
                    vehicle.setDateCreated(LocalDateTime.now());
                    vehicle.setKilometerDriven(vehicleModel.getKilometerDriven());
                    vehicle.setRegistrationNumber(vehicleModel.getRegistrationNumber());
                    Vehicle vehicle1 = vehicleRepository.save(vehicle);
                    return vehicle1.getId();
                }
//                else {
////                  userRepository.insertVehicleCustomers(vehicleModel.getOwnerId());
////                    Optional<User> owner1 = userRepository.findById(vehicleModel.getOwnerId());
////
////                    vehicle.setCarOwner(owner1.get());
////                    vehicle.setMake(vehicleModel.getMake());
////                    vehicle.setColor(vehicleModel.getColor());
////                    vehicle.setModel(vehicleModel.getModel());
////                    vehicle.setYear(vehicleModel.getYear());
////                    vehicle.setType(vehicleModel.getType());
////                    vehicle.setKilometerDriven(vehicleModel.getKilometerDriven());
////                    vehicle.setRegistrationNumber(vehicleModel.getRegistrationNumber());
////                    vehicleRepository.save(vehicle);
//                    return ;
//                }
            } else {
                throw new RecordNotFoundException("Business not found");
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    public List<Vehicle> getAllVehiclesOfBusiness(Long businessId) {
        return vehicleRepository.findAllByAssociatedToBusinessId(businessId);
    }

    public Vehicle getVehicle(Long id) {
        return vehicleRepository.findById(id).get();
    }


    public Optional<Vehicle> edit_details(Long id) {
        Optional<Vehicle> foundVehicle = vehicleRepository.findById(id);
        return foundVehicle;
    }

    public boolean updateVehicle(Long businessId, Long vehicleId, VehicleModel vehicleModel) {

        try {
            Optional<Business> business = businessRepository.findById(businessId);
            if (business.isPresent()) {
                Optional<User> owner = userRepository.findById(vehicleModel.getOwnerId());
                if (owner.isPresent()) {

                    Vehicle updatableVehicle = vehicleRepository.findById(vehicleId).get();
                    if (updatableVehicle != null) {
                        updatableVehicle.setColor(vehicleModel.getColor());
                        updatableVehicle.setModel(vehicleModel.getModel());
                        updatableVehicle.setYear(vehicleModel.getYear());
                        updatableVehicle.setKilometerDriven(vehicleModel.getKilometerDriven());
                        updatableVehicle.setRegistrationNumber(vehicleModel.getRegistrationNumber());
                        updatableVehicle.setType(vehicleModel.getType());
                        updatableVehicle.setMake(vehicleModel.getMake());
                        vehicleRepository.save(updatableVehicle);
                        return true;
                    } else {
                        throw new RecordNotFoundException("Vehicle not found");
                    }
                } else {
                    throw new RecordNotFoundException("User not found");

                }
            } else {
                throw new RecordNotFoundException("Business not found");
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteVehicle(Long businessId, Long vehicleId) {
        try {
            Optional<Business> business = businessRepository.findById(businessId);
            if (business.isPresent()) {
                Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
                if (vehicle.isPresent()) {
                    vehicleRepository.deleteById(vehicleId);
                    return true;
                } else {
                    throw new RecordNotFoundException("Vehicle not found");
                }
            } else {
                throw new RecordNotFoundException("Business not found");
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public void saveVehicleImage(Long profileImgId, Long vehicleId) {
        VehicleMedia savedImg = vehicleMediaRepository.findById(profileImgId).get();
        Vehicle vehicle = getVehicle(vehicleId);
        List<VehicleMedia> vehicleImages = vehicle.getVehicleMedia();
        vehicleImages.add(savedImg);
        vehicle.setVehicleMedia(vehicleImages);
        vehicleRepository.save(vehicle);
    }

}