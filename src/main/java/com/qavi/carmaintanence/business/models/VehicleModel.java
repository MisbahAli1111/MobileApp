package com.qavi.carmaintanence.business.models;

import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    String customerType;
    String parentCompany;
//    List<Map<String,Object>> vehicleMediaList;

    List<String> vehicleMediaList;
}
