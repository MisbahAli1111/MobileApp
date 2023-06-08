package com.qavi.carmaintanence.business.models;

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
