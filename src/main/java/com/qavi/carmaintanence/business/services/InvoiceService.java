package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.invoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public boolean editInvoice(Invoice invoice, Long invoiceId) {
            MaintenanceRecord maintenanceRecord = maintenancerecordrepository.findById(invoiceId).get();
            //InvoiceDue
            if(Objects.nonNull(invoice.getInvoiceDue()) &&
                    !"".equals(invoice.getInvoiceDue()))
            {
                invoice.setInvoiceDue(invoice.getInvoiceDue());
            }
            //VehicleId
            if(Objects.nonNull(invoice.getVehicleId()) &&
                    !"".equals(invoice.getVehicleId()))
            {
                invoice.setVehicleId(invoice.getVehicleId());
            }
            //Date
            if(Objects.nonNull(invoice.getDate()) &&
                    !"".equals(invoice.getDate()))
            {
                invoice.setDate(invoice.getDate());
            }
            //Description
            if(Objects.nonNull(invoice.getDescription()) &&
                    !"".equals(invoice.getDescription()))
            {
                invoice.setDescription(invoice.getDescription());
            }
            //Quantity
            if(Objects.nonNull(invoice.getQty()) &&
                    !"".equals(invoice.getQty()))
            {
                invoice.setQty(invoice.getQty());
            }
            //Rate
            if(Objects.nonNull(invoice.getRate()) &&
                    !"".equals(invoice.getRate()))
            {
                invoice.setRate(invoice.getRate());
            }
            //DiscountName
            if(Objects.nonNull(invoice.getDiscountName()) &&
                    !"".equals(invoice.getDiscountName()))
            {
                invoice.setDiscountName(invoice.getDiscountName());
            }
            //DiscountRate
            if(Objects.nonNull(invoice.getDiscountRate()) &&
                    !"".equals(invoice.getDiscountRate()))
            {
                invoice.setDiscountRate(invoice.getDiscountRate());
            }
            //TaxName
            if(Objects.nonNull(invoice.getTaxName()) &&
                    !"".equals(invoice.getTaxName()))
            {
                invoice.setTaxName(invoice.getTaxName());
            }
            //TaxRate
            if(Objects.nonNull(invoice.getTaxRate()) &&
                    !"".equals(invoice.getTaxRate()))
            {
                invoice.setTaxRate(invoice.getTaxRate());
            }

            //Unchangable entities not confirmed:  status, Maintained by id and user.

            if(maintenanceRecord !=null )
            {
                invoicerepository.save(invoice);
                return true;
            }
            else {
                return false;
            }

        }


    public Optional<Invoice> getInvoice(Long invoiceId) {
      Optional<Invoice> invoice =invoicerepository.findById(invoiceId);

        return invoice;
    }

    public List<Invoice> getAllInvoices(Invoice invoice) {
        List<Invoice> invoices = invoicerepository.findAll();

        return invoices;
    }
}
