package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.business.services.MaintenanceRecordService;
import com.qavi.carmaintanence.business.utils.MaintenanceRecordConverter;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;
import com.qavi.carmaintanence.usermanagement.utils.ConverterModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/maintenance-record")
public class MaintaenanceRecordController {
    @Autowired
    MaintenanceRecordService mrs;

    @Autowired
    VehicleRepository vehicleRepository;

    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @PostMapping("/add-record")
    public ResponseEntity<ResponseModel> addRecord(@RequestBody MaintanenceRecordModel maintenanceRecordModel, Authentication authentication)
    {

        Long userId = Long.parseLong(authentication.getName());

        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Maintenance Record added successfully")
                .data(new Object())
                .build();

        Optional<Vehicle> vehicle = vehicleRepository.findByRegistrationNumber(maintenanceRecordModel.getRegistrationNumber());

        if(vehicle.isPresent()) {
            if (!mrs.addRecord(maintenanceRecordModel, userId)) {
                responseModel.setMessage("Failed To Add Record");
                responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
            }
        }
        else{
            responseModel.setMessage("No Vehicle Found");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @GetMapping("/get-records")
    public ResponseEntity<List<MaintanenceRecordModel>> getallrecords()
    {

        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("All  Records Are Founded successfully")
                .data(new Object())
                .build();
        List<MaintenanceRecord> records =mrs.getallrecords();
        List convertedList= new ArrayList();
        if(records.isEmpty() || records==null)
        {
                       responseModel.setStatus(HttpStatus.NOT_FOUND);

                 responseModel.setMessage("No record Found");
            return new ResponseEntity<>(convertedList, HttpStatus.NOT_FOUND);
        }
          else {

            for (MaintenanceRecord record :records) {
                MaintanenceRecordModel maintanenceRecordModel =MaintenanceRecordConverter.covertMRtoMRmodel(record);
                convertedList.add(maintanenceRecordModel);
            }
            return new ResponseEntity<List<MaintanenceRecordModel>>(convertedList, HttpStatus.OK);
        }

    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @GetMapping("/get-customer/{registration_number}")
    public ResponseEntity<List<MaintanenceRecordModel>> getCustomerByVehicleRegistration(@PathVariable String registration_number) {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Record Founded successfully")
                .data(new Object())
   -             .build();


        Optional<User> userOptional = vehicleRepository.getUserNameFromRegistrationNumber(registration_number);
        List convertedList= new ArrayList();
        if(userOptional.isPresent())
        {
            User user=userOptional.get();
            UserDataModel userDataModel = ConverterModels.convertUserToUserDataModel(user);
            convertedList.add(userDataModel);
        }

        else {

                responseModel.setStatus(HttpStatus.NOT_FOUND);
                responseModel.setMessage("Failed to Fetch Records");
                return new ResponseEntity<>(convertedList, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<List<MaintanenceRecordModel>>(convertedList, HttpStatus.OK);
    }



    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @GetMapping("/get-records/{Id}")
    public ResponseEntity<List<MaintanenceRecordModel>> getRecordById(@PathVariable Long Id)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Record Founded successfully")
                .data(new Object())
                .build();
        Optional<MaintenanceRecord> myRecords = mrs.getMaintenanceRecordById(Id);
        List convertedList= new ArrayList();

        if(myRecords.isPresent())
        {
            MaintenanceRecord record=myRecords.get();
                MaintanenceRecordModel maintanenceRecordModel =MaintenanceRecordConverter.covertMRtoMRmodel(record);
                convertedList.add(maintanenceRecordModel);

        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Failed to Fetch Records");
            return new ResponseEntity<>(convertedList, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<MaintanenceRecordModel>>(convertedList, HttpStatus.OK);

    }


    @PutMapping("/edit-record/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> editRecord(@RequestBody MaintenanceRecord maintenanceRecord,@PathVariable("id") Long Id){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Record Updated Successfully")
                .data(new Object())
                .build();
        if(!mrs.editMaintenanceRecord(maintenanceRecord,Id))
        {
            responseModel.setMessage("Failed To Update Record");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @DeleteMapping("/{Id}/delete-records")
    public ResponseEntity<ResponseModel> deleteRecord(@PathVariable Long Id)
    {
        ResponseModel responseModel=ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Record has been Deleted Successfully")
                .data(new Object())
                .build();
        if(!mrs.deleteRecord(Id))
        {
            responseModel.setMessage("Failed To Delete Record");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

}
