package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.business.services.MaintenanceRecordMediaService;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/maintenance-record")
public class MaintaenanceRecordController {
    @Autowired
    MaintenanceRecordService maintenanceRecordService;

    @Autowired
    MaintenanceRecordMediaService maintenanceRecordMediaService;

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
            Long record_id = maintenanceRecordService.addRecord(maintenanceRecordModel,userId);
            if (record_id == null) {
                responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
                responseModel.setMessage("Failed to create Record");
            } else {
                responseModel.setData(record_id); // Set the businessId in the response
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
        List<MaintenanceRecord> records =maintenanceRecordService.getallrecords();
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
    @GetMapping("/{id}/registration-number")
    public String getRegistrationFromRecords(@PathVariable Long id)
    {

        String registration_Number =maintenanceRecordService.findRegistartionNumberFromRecords(id);
        return registration_Number;

    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @GetMapping("/get-customer/{registration_number}")
    public ResponseEntity<List<MaintanenceRecordModel>> getCustomerByVehicleRegistration(@PathVariable String registration_number) {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Record Founded successfully")
                .data(new Object())
               .build();


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

    @GetMapping("/get-registration-number/{businessId}")
    public ResponseEntity<List<Map<String,Object>>> findRegNumber(@PathVariable Long businessId) {
        var users = vehicleRepository.findRegistrationNumberInBusiness(businessId);

        return new ResponseEntity<>(users, HttpStatus.OK);
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
        MaintenanceRecord myRecords = maintenanceRecordService.getRecord(Id);
        List<MaintanenceRecordModel> convertedList= new ArrayList();

        if(myRecords != null)
        {
            MaintenanceRecord record = myRecords; // No need for myRecords.get() since myRecords is already the record
            MaintanenceRecordModel maintanenceRecordModel = MaintenanceRecordConverter.covertMRtoMRmodel(record);
            convertedList.add(maintanenceRecordModel);

        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Failed to Fetch Records");
            return new ResponseEntity<>(convertedList, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(convertedList, HttpStatus.OK);

    }


    @PutMapping("/edit-record/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> editRecord(@RequestBody MaintenanceRecord maintenanceRecord,@PathVariable("id") Long Id){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Record Updated Successfully")
                .data(new Object())
                .build();
        if(!maintenanceRecordService.editMaintenanceRecord(maintenanceRecord,Id))
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
        if(!maintenanceRecordService.deleteRecord(Id))
        {
            responseModel.setMessage("Failed To Delete Record");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @GetMapping("/{Id}/images")
    public ResponseEntity <List<Map<String,Object>>> getVehicleImages(@PathVariable Long Id)
    {
        List<Map<String,Object>> imageData = maintenanceRecordMediaService.getVehicleImagesData(Id);
        return ResponseEntity.ok(imageData);
    }
}
