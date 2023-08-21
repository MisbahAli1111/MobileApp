package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.InvoiceDescription;
import com.qavi.carmaintanence.business.entities.InvoiceDiscount;
import com.qavi.carmaintanence.business.entities.InvoiceTax;
import com.qavi.carmaintanence.business.models.InvoiceModel;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceConverter {
    public static InvoiceModel convertInvoiceToInvoiceModel(Invoice invoice) {
        InvoiceModel invoiceModel= new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setDate(invoice.getDate());
        invoiceModel.setInvoiceDue(invoice.getInvoiceDue());

        List<InvoiceDescription> descriptions = invoice.getDescriptions();
        List<Map<String, Object>> descriptionDataList = new ArrayList<>();
        for (InvoiceDescription description : descriptions) {
            descriptionDataList.add(convertDescriptionToMap(description));
        }
        invoiceModel.setDescriptions(descriptionDataList);

        List<InvoiceDiscount> discounts = invoice.getDiscounts();
        List<Map<String, Object>> discountDataList = new ArrayList<>();
        for (InvoiceDiscount discount : discounts) {
            discountDataList.add(convertDiscountToMap(discount));
        }
        invoiceModel.setDiscounts(discountDataList);

        List<InvoiceTax> taxes = invoice.getTaxes();
        List<Map<String, Object>> taxDataList = new ArrayList<>();
        for (InvoiceTax tax : taxes) {
            taxDataList.add(convertTaxToMap(tax));
        }
        invoiceModel.setTaxes(taxDataList);

        return invoiceModel;
    }

    // Example conversion methods
    private static Map<String, Object> convertDescriptionToMap(InvoiceDescription description) {
        Map<String, Object> descriptionMap = new HashMap<>();
        descriptionMap.put("item", description.getItem());
        descriptionMap.put("rate", description.getRate());
        descriptionMap.put("quantity", description.getQuantity());
        descriptionMap.put("amount", description.getAmount());
        return descriptionMap;
    }

    private static Map<String, Object> convertDiscountToMap(InvoiceDiscount discount) {
        Map<String, Object> discountMap = new HashMap<>();
        discountMap.put("discountName", discount.getDiscountName());
        discountMap.put("discountRate", discount.getDiscountRate());
        return discountMap;
    }

    private static Map<String, Object> convertTaxToMap(InvoiceTax tax) {
        Map<String, Object> taxMap = new HashMap<>();
        taxMap.put("taxName", tax.getTaxName());
        taxMap.put("taxRate", tax.getTaxRate());
        return taxMap;
    }





    public static Invoice convertInvoiceModelToInvoice(InvoiceModel model) {
        Invoice invoice = new Invoice();
        //Get Data from Frontend as List.
        List<Map<String, Object>> fetchedTaxes = model.getTaxes();
        List<Map<String, Object>> fetchedDiscounts = model.getDiscounts();
        List<Map<String, Object>> fetchedDescriptions = model.getDescriptions();

        //Make a List to append those list in Backend
        List<InvoiceTax> taxes = new ArrayList<>();
        List<InvoiceDiscount> discounts = new ArrayList<>();
        List<InvoiceDescription> descriptions = new ArrayList<>();

        //Loop through to save all details of InvoiceTax
        for (Map<String,Object> Tax : fetchedTaxes) {
            InvoiceTax tax = new InvoiceTax();
            tax.setTaxName((String) Tax.get("taxName"));
            tax.setTaxRate((Double) Tax.get("taxRate"));
            taxes.add(tax);
        }
        invoice.setTaxes(taxes);

        //Loop through to save all details of InvoiceDiscounts
        for (Map<String,Object> Discount : fetchedDiscounts) {
            InvoiceDiscount discount = new InvoiceDiscount();
            discount.setDiscountName((String) Discount.get("discountName"));
            discount.setDiscountRate((Double) Discount.get("discountRate"));
            discounts.add(discount);
        }
        invoice.setDiscounts(discounts);

        //Loop through to save all details of InvoiceDescription
        for(Map<String,Object> Description : fetchedDescriptions)
        {
            InvoiceDescription description = new InvoiceDescription();
            description.setItem((String) (Description.get("item")));
            description.setRate((Double) (Description.get("rate")));
            description.setQuantity((Double) (Description.get("quantity")));
            description.setAmount((Double) (Description.get("amount")));
            descriptions.add(description);
        }
        invoice.setDescriptions(descriptions);

        //return an invoice from the model
        return invoice;
    }
}
