package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.services.InvoiceService;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Controller
public class InvoiceController {


    @Autowired
    InvoiceService invoiceService;
    @GetMapping("/get-invoice")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> getAllInvoice(@PathVariable Invoice invoice){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice Created Successfully")
                .data(new Object())
                .build();
        List<Invoice> invoices=invoiceService.getAllInvoices(invoice);
        if(invoices.size()>0)
        {
            responseModel.setData(invoices);
        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Invoices not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @GetMapping("/get-invoice/{invoice_id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> getOneInvoice(@PathVariable Long invoiceId){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice Details Found Successfully")
                .data(new Object())
                .build();
        Optional<Invoice> invoices=invoiceService.getInvoice(invoiceId);
        if(invoices.isPresent())
        {
            responseModel.setData(invoices);
        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Failed to Fetch Invoice Detail");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PostMapping("/create-invoice/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> createInvoice(@RequestBody Invoice invoice, @PathVariable Long id ){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice Created Successfully")
                .data(new Object())
                .build();
        if(!invoiceService.addInvoice(invoice,id))
        {
            responseModel.setMessage("Failed To Create Invoice");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/edit-invoice")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> editInvoice(@RequestBody Invoice invoice,@PathVariable Long Id){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice Created Successfully")
                .data(new Object())
                .build();
        if(!invoiceService.editInvoice(invoice,Id))
        {
            responseModel.setMessage("Failed To Edit Invoice");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }


}
