package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/maintenance-record")
public class MaintaenanceRecordController {
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    @PostMapping("/add-record")
    public ResponseEntity<ResponseModel> addRecord(@RequestBody MaintenanceRecord maintenanceRecord)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Maintenance Record added successfully")
                .data(new Object())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}
