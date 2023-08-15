package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.models.BusinessModel;
import com.qavi.carmaintanence.business.models.InvoiceModel;
import com.qavi.carmaintanence.business.services.BusinessService;
import com.qavi.carmaintanence.business.utils.BusinessConverter;
import com.qavi.carmaintanence.business.utils.InvoiceConverter;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<ResponseModel> getBusinesses(@AuthenticationPrincipal String id) {
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Businesses Fetched Successfully")
                .data(new ArrayList<>()) // Initialize an empty ArrayList
                .build();

        List<Business> myBusinesses = businessService.getMyBusinesses(id);
        List<BusinessModel> convertedList = new ArrayList<>();

        if (myBusinesses.size() > 0) {
            for (Business business : myBusinesses) {
                convertedList.add(BusinessConverter.convertBusinessToBusinessModel(business));
            }
            responseModel.setData(convertedList);
        } else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("No Business Found");
        }
        return ResponseEntity.status(responseModel.getStatus()).body(responseModel);
    }


    @GetMapping("/get-business")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> getAllBusiness(){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Business Found Successfully")
                .data(new Object())
                .build();
        List<Business> myBusinesses=businessService.getAllBusiness();
        if(myBusinesses.size()>0)
        {
            responseModel.setData(myBusinesses);
        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("business not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }




    @PutMapping("/edit-business")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> editBusiness(@RequestBody Business business, @PathVariable Long id){
        ResponseModel responseModel=ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Business Edited Successfully")
                .data(new Object())
                .build();
        if(!businessService.editBusiness(business,id)){
            responseModel.setMessage("Failed To Edit Business");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }


    @DeleteMapping("/{Id}/delete-business")
    public ResponseEntity<ResponseModel> deleteBusinesses(@PathVariable Long Id)
    {
        ResponseModel responseModel=ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Business has been Deleted Successfully")
                .data(new Object())
                .build();
        if(!businessService.deleteBusiness(Id))
        {
            responseModel.setMessage("Failed To Delete Business");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

}
