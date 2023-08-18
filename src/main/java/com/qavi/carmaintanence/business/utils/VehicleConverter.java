package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.models.VehicleModel;

public class VehicleConverter {

    public static VehicleModel convertVehicleToVehicleModel(Vehicle vehicle) {
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setModel(vehicle.getModel());
        vehicleModel.setColor(vehicle.getColor());
        vehicleModel.setMake(vehicle.getMake());
        vehicleModel.setType(vehicle.getType());
        vehicleModel.setYear(vehicle.getYear());
        vehicleModel.setKilometerDriven(vehicle.getKilometerDriven());
        vehicleModel.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleModel.setId(vehicle.getId());
        vehicleModel.setFirstName(vehicle.getCarOwner().getFirstName());
        vehicleModel.setLastName(vehicle.getCarOwner().getLastName());
        vehicleModel.setPhoneNumber(vehicle.getCarOwner().getPhoneNumber());
        vehicleModel.setOwnerId(vehicle.getCarOwner().getId());
        vehicleModel.setDateCreated(vehicle.getDateCreated());

        //        businessModel.setDescription(business.getDescription());
//        businessModel.setDiscountName(business.getDiscountName());
//        businessModel.setTaxName(business.getTaxName());
//        businessModel.setTaxRate(business.getTaxRate());


        return vehicleModel;
    }
}
