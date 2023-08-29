package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.models.VehicleModel;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.business.services.VehicleService;
import com.qavi.carmaintanence.business.utils.VehicleConverter;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    VehicleRepository vehiclerepository;

//    @Autowired
//    VehicleMedia vehicleMedia;
//
//    @Autowired
//    VehicleMediaRepository vehicleMediaRepository;

    @PostMapping("/{businessId}/add-vehicle")
    public ResponseEntity<ResponseModel> addVehicle(@PathVariable Long businessId, @RequestBody VehicleModel vehicle)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicle Added Successfully")
                .data(new Object())
                .build();
        Long vehicle_id = vehicleService.addVehicle(businessId,vehicle);
        if (vehicle_id == null) {
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            responseModel.setMessage("Failed to create Vehicle");
        } else {
            responseModel.setData(vehicle_id); // Set the businessId in the response
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
    @GetMapping("/{businessId}/get-all-vehicles")
    public ResponseEntity<ResponseModel> getAllVehicles(@PathVariable Long businessId) {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicles Fetched Successfully")
                .data(new Object())
                .build();

        List<Vehicle> vehicles = vehicleService.getAllVehiclesOfBusiness(businessId);
        List<VehicleModel> convertedList = new ArrayList<>();

        if (!vehicles.isEmpty()) {
            for (Vehicle vehicle : vehicles) {
                convertedList.add(VehicleConverter.convertVehicleToVehicleModel(vehicle));
            }
            responseModel.setData(convertedList);
        } else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Vehicles not found");
        }

        return ResponseEntity.status(responseModel.getStatus()).body(responseModel);
    }




    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleModel> getOneVehicle(@PathVariable Long vehicleId)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicle Fetched Successfully")
                .data(new Object())
                .build();
        Vehicle vehicle =vehicleService.getVehicle(vehicleId);
        //List<VehicleModel> convertedList = new ArrayList<>()
//            responseModel.setData(vehicle.get());
        VehicleModel vehicleModel = VehicleConverter.convertVehicleToVehicleModel(vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleModel);
    }

    //    @GetMapping("/{vehicle_id}")
//    public ResponseEntity<ResponseModel> edit_details(@PathVariable Long id )
//    {
//        ResponseModel responseModel= ResponseModel.builder()
//                .status(HttpStatus.OK)
//                .message("Edit Your vehicle")
//                .data(new Object())
//                .build();
//        Optional<Vehicle> fetchedVehicle=vehicleService.edit_details(id);
//        if(fetchedVehicle.isEmpty()){
//
//            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
//            responseModel.setMessage("Failed to fetch vehicle detail");
//        }
//        else{
//            responseModel.setData(fetchedVehicle.get());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
//    }
    @PostMapping("/{businessId}/{vehicle_id}/update-vehicle")
    public  ResponseEntity<ResponseModel> updateVehicle(@PathVariable Long businessId, @PathVariable Long vehicle_id,@RequestBody VehicleModel vehicleModel)
    {
        ResponseModel responseModel= ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicle Updated Successfully")
                .data(new Object())
                .build();


        if(!vehicleService.updateVehicle(businessId,vehicle_id,vehicleModel))
        {

            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            responseModel.setMessage("Failed to update vehicle detail");
        }
        else{
            Vehicle fetchedVehicle=vehiclerepository.findById(vehicle_id).get();

            responseModel.setData(fetchedVehicle);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);

    }

    @DeleteMapping("/{businessId}/{vehicleId}/delete-vehicle")
    public ResponseEntity<ResponseModel> deleteVehicle(@PathVariable Long businessId,Long vehicleId)
    {
        ResponseModel responseModel=ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Vehicle has been Deleted Successfully")
                .data(new Object())
                .build();
        if(!vehicleService.deleteVehicle(businessId,vehicleId))
        {
            responseModel.setMessage("Failed To Delete Vehicle");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }


}