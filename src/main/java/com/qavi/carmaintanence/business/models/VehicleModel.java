package com.qavi.carmaintanence.business.models;

import com.qavi.carmaintanence.business.entities.Vehicle;
import lombok.Data;

import java.time.LocalDateTime;

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
    LocalDateTime dateCreated;
    Long ownerId;
String name;
    String phoneNumber;


}
