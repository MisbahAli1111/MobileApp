package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.invoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
 @Autowired
 private invoiceRepository invoicerepository;
@Autowired
private MaintenanceRecordRepository mr;




    public boolean addInvoice(Invoice invoice,Long id) {
        MaintenanceRecord m1= new MaintenanceRecord();
        m1= mr.findById(id).get();
        if (m1!=null) {
            invoicerepository.save(invoice);
        return  true;
        }
        System.out.println( "ID not found");
     return false;
    }
    public boolean editInvoice(Invoice invoice) {
        return true;
    }


    public Optional<Invoice> getInvoice(Long invoiceId) {
     return  invoicerepository.findById(invoiceId);

    }

    public List<Invoice> getAllInvoices(Invoice invoice) {
        List<Invoice> invoices = new ArrayList<>();

        return invoices;
    }
}
