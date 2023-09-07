package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.*;
import com.qavi.carmaintanence.business.repositories.BusinessRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.business.models.InvoiceModel;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.invoiceRepository;
import com.qavi.carmaintanence.business.utils.InvoiceConverter;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.services.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InvoiceService {
    @Autowired
    MaintenanceRecordRepository maintenancerecordrepository;
    @Autowired
    invoiceRepository invoicerepository ;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    EmailService emailService;

    public boolean addInvoice(InvoiceModel invoiceModel, Long businessId,Long id,Long userId) {
        MaintenanceRecord foundRecord = maintenancerecordrepository.findById(id).orElse(null);
        Optional<Long> optionalId = vehicleRepository.getVehicleIdByRegistrationNumber(invoiceModel.getRegistrationNumber());

        if (foundRecord != null) {
            Invoice invoice = new Invoice();



                invoice = InvoiceConverter.convertInvoiceModelToInvoice(invoiceModel);

                invoice.setBusiness(businessRepository.findById(businessId).get());
                invoice.setEnabled(true);
                invoice.setInvoiceDue(invoiceModel.getInvoiceDue());
                invoice.setDate(invoiceModel.getDate());
                invoice.setVehicleId(optionalId.get());
                invoice.setTotal(invoiceModel.getTotal());
                invoice.setMaintainedById(userId);
                invoice.setMaintenanceRecord(foundRecord);
                invoice.setStatus(invoiceModel.isStatus());

                invoicerepository.save(invoice);

                return true;


        } else {
            return false;
        }
    }

    public boolean editInvoice(InvoiceModel invoiceModel, Long invoiceId) {
        Optional<Invoice> optionalInvoice = invoicerepository.findById(invoiceId);

        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setInvoiceDue(invoiceModel.getInvoiceDue());
            invoice.setDate(invoiceModel.getDate());
//            invoice.setVehicleId(optionalId.get());
            invoice.setTotal(invoiceModel.getTotal());
//            invoice.setMaintainedById(userId);
//            invoice.setMaintenanceRecord(foundRecord);
            invoice.setStatus(invoiceModel.isStatus());
//            invoice = InvoiceConverter.convertInvoiceModelToInvoice(invoiceModel);

            // Update taxes
            List<Tax> updatedTaxes = new ArrayList<>();
            List<Map<String, Object>> fetchedTaxes = invoiceModel.getTaxes();
            for (Map<String, Object> taxMap : fetchedTaxes) {
                Tax tax = new Tax();
                tax.setTaxName((String) taxMap.get("taxName"));
                tax.setTaxRate(((Number) taxMap.get("taxRate")).doubleValue());
                updatedTaxes.add(tax);
            }
            invoice.setTaxes(updatedTaxes);

            // Update discounts
            List<Discount> updatedDiscounts = new ArrayList<>();
            List<Map<String, Object>> fetchedDiscounts = invoiceModel.getDiscounts();
            for (Map<String, Object> discountMap : fetchedDiscounts) {
                Discount discount = new Discount();
                discount.setDiscountName((String) discountMap.get("discountName"));
                discount.setDiscountRate(((Number) discountMap.get("discountRate")).doubleValue());
                updatedDiscounts.add(discount);
            }
            invoice.setDiscounts(updatedDiscounts);

            // Update descriptions
            List<Item> updatedDescriptions = new ArrayList<>();
            List<Map<String, Object>> fetchedDescriptions = invoiceModel.getDescriptions();
            for (Map<String, Object> descriptionMap : fetchedDescriptions) {
                Item description = new Item();
                description.setItem((String) descriptionMap.get("item"));
                description.setRate(((Number) descriptionMap.get("rate")).doubleValue());
                description.setQuantity((int) descriptionMap.get("quantity"));
                description.setAmount(((Number) descriptionMap.get("amount")).doubleValue());
                updatedDescriptions.add(description);
            }
            invoice.setDescriptions(updatedDescriptions);



            invoicerepository.save(invoice);
            return true;
        } else {
            return false;
        }
    }




    public Optional<Invoice> getInvoice(Long invoiceId) {
        Optional<Invoice> invoice =invoicerepository.findById(invoiceId);

        return invoice;
    }

    public List<Invoice> getAllInvoices(Long businessId) {
        List<Invoice> invoices = invoicerepository.findAllByBusinessIdAndEnabledIsTrue(businessId);
        return invoices;
    }

    public boolean deleteInvoice(Long id) {
        try {
            Optional<Invoice> invoice = invoicerepository.findById(id);
            if (invoice.isPresent()) {
                long invoiceId=id;
                invoicerepository.UpdateInvoiceEnabled(invoiceId);
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

    @Scheduled(cron = "0 0 * * * ?")
    public void findInvoiceDue() {
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrow = currentDate.plusDays(1);

        List<Invoice> invoiceDueRecords = invoicerepository.findByInvoiceDueBetween(tomorrow);

        if (invoiceDueRecords != null && !invoiceDueRecords.isEmpty()) {
            for (Invoice record : invoiceDueRecords) {
                String email = record.getMaintenanceRecord().getVehicle().getCarOwner().getEmail();
                String business_profile = record.getBusiness().getBusinessProfileImage().toString();
                System.out.println(business_profile);
                String subject = "Invoice Due Notification";

                String[] parts = email.split("\\@");
                String recipientFirstName = parts[0];

                String Message = "Hi " + recipientFirstName + ",\n" +"Your invoice is due tomorrow. Kindly pay it before deadline." ;

                // Send the email
                emailService.invoiceDueEmail(email, subject, Message,business_profile);

            }
        } else {
            System.out.println("No service due records found for tomorrow (" + tomorrow + ")");
        }
    }

}