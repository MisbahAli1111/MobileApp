package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.invoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
@Autowired
    MaintenanceRecordRepository maintenancerecordrepository;
@Autowired
invoiceRepository invoicerepository ;



    public boolean addInvoice(Invoice invoice,Long id) {
       MaintenanceRecord foundRecord =maintenancerecordrepository.findById(id).get();
       if(foundRecord !=null )
       {
           invoicerepository.save(invoice);
        return true;
       }
       else {
        return    false;
       }

    }

    public boolean editInvoice(Invoice invoice) {

        return true;
    }


    public Optional<Invoice> getInvoice(Long invoiceId) {
      Optional<Invoice> invoice =invoicerepository.findById(invoiceId);

        return invoice;
    }

    public List<Invoice> getAllInvoices(Invoice invoice) {
        List<Invoice> invoices = new ArrayList<>();

        return invoices;
    }
}
