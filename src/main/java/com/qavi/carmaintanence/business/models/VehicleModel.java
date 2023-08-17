package com.qavi.carmaintanence.business.models;

import com.qavi.carmaintanence.business.entities.Vehicle;
import lombok.Data;

@Data
public class VehicleModel {
    Long id;
    String type;
    String model;
    String make;
    String year;
    String registrationNumber;
    String color;
    double kilometerDriven;
    Long ownerId;
    String firstName;
    String lastName;
    String phoneNumber;


}
