package com.qavi.carmaintanence.business.controllers;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.models.InvoiceModel;
import com.qavi.carmaintanence.business.services.InvoiceService;
import com.qavi.carmaintanence.business.utils.InvoiceConverter;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.ResponseModel;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;
import com.qavi.carmaintanence.usermanagement.utils.ConverterModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {


    @Autowired
    InvoiceService invoiceService;
    @GetMapping("/get-invoice")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<List<InvoiceModel>> getAllInvoice(){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoices Recived Successfully")
                .data(new Object())
                .build();
        List<Invoice> invoices=invoiceService.getAllInvoices();
        List<InvoiceModel> convertedList = new ArrayList<>();

        if(invoices.size()>0)
        {
            responseModel.setData(invoices);
            for (Invoice invoice : invoices) {
                convertedList.add(InvoiceConverter.convertInvoiceToInvoiceModel(invoice));
            }
            return new ResponseEntity<List<InvoiceModel>>(convertedList, HttpStatus.OK);

        }
        else {
            responseModel.setStatus(HttpStatus.NOT_FOUND);

            responseModel.setMessage("Invoices not found");

            return new ResponseEntity<>(convertedList, HttpStatus.NOT_FOUND);

        }
    }
    @GetMapping("/get-invoice/{invoice_id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<List<InvoiceModel>> getOneInvoice(@PathVariable("invoice_id") Long invoiceId) {
        Optional<Invoice> invoiceOptional = invoiceService.getInvoice(invoiceId);

        List<InvoiceModel> convertedList = null;
        if (invoiceOptional.isPresent()) {
            Invoice invoice = invoiceOptional.get();
            InvoiceModel invoiceModel = InvoiceConverter.convertInvoiceToInvoiceModel(invoice);
            convertedList = new ArrayList<>();
            convertedList.add(invoiceModel);

            ResponseModel responseModel = ResponseModel.builder()
                    .status(HttpStatus.OK)
                    .message("Invoice Details Found Successfully")
                    .data(convertedList)
                    .build();

            return new ResponseEntity<>(convertedList, HttpStatus.OK);
        } else {
            ResponseModel responseModel = ResponseModel.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Failed to Fetch Invoice Detail")
                    .build();

            return new ResponseEntity<>(convertedList, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-invoice/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> createInvoice(@RequestBody InvoiceModel invoiceModel, @PathVariable Long id ){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice Created Successfully")
                .data(new Object())
                .build();
        if(!invoiceService.addInvoice(invoiceModel,id))
        {
            responseModel.setMessage("Failed To Create Invoice");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/edit-invoice/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','OWNER')")
    public ResponseEntity<ResponseModel> editInvoice(@RequestBody Invoice invoice,@PathVariable Long id){
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice updated Successfully")
                .data(new Object())
                .build();
        if(!invoiceService.editInvoice(invoice,id))
        {
            responseModel.setMessage("Failed To Edit Invoice");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @DeleteMapping("/{Id}/delete-invoice")
    public ResponseEntity<ResponseModel> deleteInvoices(@PathVariable Long Id)
    {
        ResponseModel responseModel=ResponseModel.builder()
                .status(HttpStatus.OK)
                .message("Invoice has been Deleted Successfully")
                .data(new Object())
                .build();
        if(!invoiceService.deleteInvoice(Id))
        {
            responseModel.setMessage("Failed To Delete Invoice");
            responseModel.setStatus(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }


}

