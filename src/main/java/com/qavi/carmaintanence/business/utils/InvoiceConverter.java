package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.Item;
import com.qavi.carmaintanence.business.entities.Discount;
import com.qavi.carmaintanence.business.entities.Tax;
import com.qavi.carmaintanence.business.models.InvoiceModel;

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
        invoiceModel.setMaintainedById(invoice.getMaintainedById());
        invoiceModel.setTotal(invoice.getTotal());
        List<Item> descriptions = invoice.getDescriptions();
        List<Map<String, Object>> descriptionDataList = new ArrayList<>();
        for (Item description : descriptions) {
            descriptionDataList.add(convertDescriptionToMap(description));
        }
        invoiceModel.setDescriptions(descriptionDataList);

        List<Discount> discounts = invoice.getDiscounts();
        List<Map<String, Object>> discountDataList = new ArrayList<>();
        for (Discount discount : discounts) {
            discountDataList.add(convertDiscountToMap(discount));
        }
        invoiceModel.setDiscounts(discountDataList);

        List<Tax> taxes = invoice.getTaxes();
        List<Map<String, Object>> taxDataList = new ArrayList<>();
        for (Tax tax : taxes) {
            taxDataList.add(convertTaxToMap(tax));
        }
        invoiceModel.setTaxes(taxDataList);

        return invoiceModel;
    }

    // Example conversion methods
    private static Map<String, Object> convertDescriptionToMap(Item description) {
        Map<String, Object> descriptionMap = new HashMap<>();
        descriptionMap.put("item", description.getItem());
        descriptionMap.put("rate", description.getRate());
        descriptionMap.put("quantity", description.getQuantity());
        descriptionMap.put("amount", description.getAmount());
        return descriptionMap;
    }

    private static Map<String, Object> convertDiscountToMap(Discount discount) {
        Map<String, Object> discountMap = new HashMap<>();
        discountMap.put("discountName", discount.getDiscountName());
        discountMap.put("discountRate", discount.getDiscountRate());
        return discountMap;
    }

    private static Map<String, Object> convertTaxToMap(Tax tax) {
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
        List<Tax> taxes = new ArrayList<>();
        List<Discount> discounts = new ArrayList<>();
        List<Item> descriptions = new ArrayList<>();

        //Loop through to save all details of InvoiceTax
        for (Map<String,Object> Tax : fetchedTaxes) {
            com.qavi.carmaintanence.business.entities.Tax tax = new Tax();
            tax.setTaxName((String) Tax.get("taxName"));
            tax.setTaxRate((double) Tax.get("taxRate"));
            taxes.add(tax);
        }
        invoice.setTaxes(taxes);

        //Loop through to save all details of InvoiceDiscounts
        for (Map<String,Object> Discount : fetchedDiscounts) {
            com.qavi.carmaintanence.business.entities.Discount discount = new Discount();
            discount.setDiscountName((String) Discount.get("discountName"));
            discount.setDiscountRate((double) Discount.get("discountRate"));
            discounts.add(discount);
        }
        invoice.setDiscounts(discounts);

        //Loop through to save all details of InvoiceDescription
        for(Map<String,Object> Description : fetchedDescriptions)
        {
            Item description = new Item();
            description.setItem((String) (Description.get("item")));
            description.setRate((double) (Description.get("rate")));
            description.setQuantity((int) (Description.get("quantity")));
            description.setAmount((double) (Description.get("amount")));
            descriptions.add(description);
        }
        invoice.setDescriptions(descriptions);

        //return an invoice from the model
        return invoice;
    }
}
