package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.InvoiceDescription;
import com.qavi.carmaintanence.business.models.DescriptionModel;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.models.DescriptionModel;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.invoiceRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
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



    public boolean addInvoice(Invoice invoice, Long id) {
        MaintenanceRecord foundRecord = maintenancerecordrepository.findById(id).orElse(null);

        if (foundRecord != null) {
            Invoice invoiceModel = new Invoice();
            invoiceModel.setInvoiceDue(invoice.getInvoiceDue());
            invoiceModel.setDate(invoice.getDate());
            invoiceModel.setVehicleId(invoice.getVehicleId());
            invoiceModel.setTotal(invoice.getTotal());

            List<InvoiceDescription> descriptions = new ArrayList<>();
            for (DescriptionModel descriptionModel : invoice.getDescriptions()) {
                InvoiceDescription description = new InvoiceDescription();
                description.setItem(descriptionDTO.getItem());
                description.setRate(descriptionDTO.getRate());
                description.setQuantity(descriptionDTO.getQuantity());
                description.setAmount(descriptionDTO.getAmount());
                description.setInvoice(invoiceModel);
                descriptions.add(description);
            }
            invoiceModel.setDescriptions(descriptions);

            invoiceModel.setMaintenanceRecord(foundRecord);

            invoicerepository.save(invoiceModel);
            System.out.println(foundRecord.getId());
            return true;
        } else {
            return false;
        }
    }


    public boolean editInvoice(Invoice invoice, Long invoiceId) {
           // MaintenanceRecord maintenanceRecord = maintenancerecordrepository.findById(invoiceId).get();
            Invoice invoice1=invoicerepository.findById(invoiceId).get();
            //InvoiceDue
            if(Objects.nonNull(invoice.getInvoiceDue()) &&
                    !"".equals(invoice.getInvoiceDue()))
            {
                invoice1.setInvoiceDue(invoice.getInvoiceDue());
            }
            //VehicleId
            if(Objects.nonNull(invoice.getVehicleId()) &&
                    !"".equals(invoice.getVehicleId()))
            {
                invoice1.setVehicleId(invoice.getVehicleId());
            }
            //Date
            if(Objects.nonNull(invoice.getDate()) &&
                    !"".equals(invoice.getDate()))
            {
                invoice1.setDate(invoice.getDate());
            }
            //Description
//            if(Objects.nonNull(invoice.getDescription()) &&
//                    !"".equals(invoice.getDescription()))
//            {
//                invoice1.setDescription(invoice.getDescription());
//            }
//            //Quantity
//            if(Objects.nonNull(invoice.getQty()) &&
//                    !"".equals(invoice.getQty()))
//            {
//                invoice1.setQty(invoice.getQty());
//            }
//            //Rate
//            if(Objects.nonNull(invoice.getRate()) &&
//                    !"".equals(invoice.getRate()))
//            {
//                invoice1.setRate(invoice.getRate());
//            }
//            //DiscountName
//            if(Objects.nonNull(invoice.getDiscountName()) &&
//                    !"".equals(invoice.getDiscountName()))
//            {
//                invoice1.setDiscountName(invoice.getDiscountName());
//            }
//            //DiscountRate
//            if(Objects.nonNull(invoice.getDiscountRate()) &&
//                    !"".equals(invoice.getDiscountRate()))
//            {
//                invoice1.setDiscountRate(invoice.getDiscountRate());
//            }
//            //TaxName
//            if(Objects.nonNull(invoice.getTaxName()) &&
//                    !"".equals(invoice.getTaxName()))
//            {
//                invoice1.setTaxName(invoice.getTaxName());
//            }
//            //TaxRate
//            if(Objects.nonNull(invoice.getTaxRate()) &&
//                    !"".equals(invoice.getTaxRate()))
//            {
//                invoice1.setTaxRate(invoice.getTaxRate());
//            }

            //Unchangable entities not confirmed:  status, Maintained by id and user.

            if(invoice1 !=null )
            {
                invoicerepository.save(invoice1);
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

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = invoicerepository.findAll();

        return invoices;
    }

    public boolean deleteInvoice(Long id) {
        try {
            Optional<Invoice> invoice = invoicerepository.findById(id);
            if (invoice.isPresent()) {
                invoicerepository.deleteById(id);
                return true;
            } else {
                throw new RecordNotFoundException("Record not found");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
