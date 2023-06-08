package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.services.BusinessService;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/business")
public class BusinessController {
    @Autowired
    BusinessService businessService;
    @PostMapping("/add-business")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ResponseModel> addBusiness(@AuthenticationPrincipal String id, @RequestBody Business business)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Business Created Successfully")
                .data(new Object())
                .build();
        if(!businessService.addBusiness(id,business))
        {
            responseModel.setMessage("Failed To Create Business");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
    @GetMapping("/get-my-businesses")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ResponseModel> getBusinesses(@AuthenticationPrincipal String id)
    {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Businesses Fetched Successfully")
                .data(new Object())
                .build();
        List<Business> myBusinesses=businessService.getMyBusinesses(id);
        if(myBusinesses.isEmpty() || myBusinesses==null)
        {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("No Bussiness Found");
        }
        else {
            responseModel.setData(myBusinesses);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}
