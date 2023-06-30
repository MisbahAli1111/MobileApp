package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.services.MaintenanceRecordService;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
