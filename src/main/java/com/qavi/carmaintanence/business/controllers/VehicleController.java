package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.models.VehicleModel;
import com.qavi.carmaintanence.business.services.VehicleService;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;
    
    @PostMapping("/{businessId}/add-vehicle")
    public ResponseEntity<ResponseModel> addVehicle(@PathVariable Long businessId, @RequestBody VehicleModel vehicle)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicle Added Successfully")
                .data(new Object())
                .build();
        if(!vehicleService.addVehicle(businessId,vehicle))
        {
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            responseModel.setMessage("Failed to add vehicle");
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
    @GetMapping("/{businessId}/get-all-vehicles")
    public ResponseEntity<ResponseModel> getAllVehicles(@PathVariable Long businessId)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicles Fetched Successfully")
                .data(new Object())
                .build();
        List<Vehicle> vehicles=vehicleService.getAllVehiclesOfBusiness(businessId);
        if(vehicles.size()>0)
        {
            responseModel.setData(vehicles);
        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Vehicles not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ResponseModel> getOneVehicle(@PathVariable Long vehicleId)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicle Fetched Successfully")
                .data(new Object())
                .build();
        Optional<Vehicle> fetchedVehicle=vehicleService.getVehicle(vehicleId);
        if(fetchedVehicle.isEmpty()){
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            responseModel.setMessage("Failed to fetch vehicle detail");
        }
        else{
            responseModel.setData(fetchedVehicle.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
    @GetMapping("/{vehicle_id}")
    public ResponseEntity<ResponseModel> edit_details(@PathVariable Long id )
    {
        ResponseModel responseModel= ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("vehicle updated")
                .data(new Object())
                .build();
        Optional<Vehicle> fetchedVehicle=vehicleService.edit_details(id);
        if(fetchedVehicle.isEmpty()){

            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            responseModel.setMessage("Failed to fetch vehicle detail");
        }
        else{
            responseModel.setData(fetchedVehicle.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);

    }
@PostMapping("/{businessId}/{vehicle_id}/update-vehicle")
    public  void updateVehicle(@PathVariable Long businessId, @PathVariable Long vehicle_id,@RequestBody VehicleModel vehicleModel)
{

    vehicleService.updateVehicle(businessId,vehicle_id,vehicleModel);
}
}
