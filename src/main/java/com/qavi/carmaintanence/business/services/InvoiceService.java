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
//    @Autowired
//    private MaintenanceRecord m1;




    public boolean addInvoice(Invoice invoice) {
//        if (mr.findById(invoice.getId()).get()!=null) {
//            invoice.setDate(m1.getMaintanenceDateTime());
//            invoice.setDescription(m1.getDescription());
//            invoice.setVehicleId(m1.getVehicleId());
//            invoice.setAmountWithDis(m1.getAmountWithDis());
//            invoice.setQty(m1.getQty());
//            invoice.setRate(m1.getRate());
//            invoice.setDiscountName(m1.getDiscountName());
//            invoice.setAmountWithOutDis(m1.getAmountWithOutDis());
//            LocalDate currentDate = LocalDate.now();
//            LocalDate futureDate = currentDate.plusDays(15);
//            invoice.setInvoiceDue(futureDate);
//            invoice.setTaxName(m1.getTaxName());
//            invoice.setTaxRate(m1.getTaxRate());
//            invoicerepository.save(invoice);
//            return true;
//        }
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
