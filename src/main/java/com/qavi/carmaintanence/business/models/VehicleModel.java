package com.qavi.carmaintanence.business.models;

import com.qavi.carmaintanence.business.entities.Vehicle;
import lombok.Data;

@Data
public class VehicleModel {
    String type;
    String model;
    String make;
    String year;
    String registrationNumber;
    String color;
    double kilometerDriven;
    Long ownerId;


}
