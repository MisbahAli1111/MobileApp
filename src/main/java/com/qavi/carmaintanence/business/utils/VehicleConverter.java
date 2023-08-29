package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Item;
import com.qavi.carmaintanence.business.entities.Tax;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.models.VehicleModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        vehicleModel.setPhoneNumber(vehicle.getCarOwner().getPhoneNumber());
        vehicleModel.setOwnerId(vehicle.getCarOwner().getId());
        vehicleModel.setDateCreated(vehicle.getDateCreated());
        String fullName = vehicle.getCarOwner().getFirstName() + " " + vehicle.getCarOwner().getLastName();
        vehicleModel.setName(fullName);
        return vehicleModel;
    }
}
