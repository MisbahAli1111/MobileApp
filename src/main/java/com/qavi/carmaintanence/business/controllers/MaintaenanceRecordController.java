package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.services.MaintenanceRecordService;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/maintenance-record")
public class MaintaenanceRecordController {
    @Autowired
    MaintenanceRecordService mrs;

    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @PostMapping("/add-record")
    public ResponseEntity<ResponseModel> addRecord(@RequestBody MaintenanceRecord maintenanceRecord)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Maintenance Record added successfully")
                .data(new Object())
                .build();
        if(!mrs.addRecord(maintenanceRecord)){
            responseModel.setMessage("Failed To Add Record");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @GetMapping("/get-records")
    public ResponseEntity<ResponseModel> getallrecords()
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("All  Records Are Founded successfully")
                .data(new Object())
                .build();
        List<MaintenanceRecord> records =mrs.getallrecords();

        if(records.isEmpty() || records==null)
        {
                       responseModel.setStatus(HttpStatus.NOT_FOUND);
        {
        }
                 responseModel.setMessage("No record Found");
        }
          else {
                    responseModel.setData(records);
                }
                return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }


    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @GetMapping("/get-records/{Id}")
    public ResponseEntity<ResponseModel> getRecordById(@PathVariable Long Id)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("All  Records Are Founded successfully")
                .data(new Object())
                .build();
        Optional<MaintenanceRecord> myRecords = mrs.getMaintenanceRecordById(Id);
        if(myRecords.isPresent())
        {
            responseModel.setData(myRecords);
        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Failed to Fetch Invoice Detail");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }


    @PutMapping("/edit-record")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> editRecord(@RequestBody MaintenanceRecord maintenanceRecord,@PathVariable Long Id){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice Created Successfully")
                .data(new Object())
                .build();
        if(!mrs.editMaintenanceRecord(maintenanceRecord,Id))
        {
            responseModel.setMessage("Failed To Edit Invoice");
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
