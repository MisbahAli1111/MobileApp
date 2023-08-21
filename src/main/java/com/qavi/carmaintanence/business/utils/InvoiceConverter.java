package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.InvoiceDescription;
import com.qavi.carmaintanence.business.entities.InvoiceDiscount;
import com.qavi.carmaintanence.business.entities.InvoiceTax;
import com.qavi.carmaintanence.business.models.InvoiceModel;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceConverter {
    public static InvoiceModel convertInvoiceToInvoiceModel(Invoice invoice) {
        InvoiceModel invoiceModel= new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setDate(invoice.getDate());
        invoiceModel.setInvoiceDue(invoice.getInvoiceDue());
//        invoiceModel.setDescriptions(invoice.getDescriptions());
//        invoiceModel.setDiscounts(invoice.getDiscounts());
//        invoiceModel.setTaxes(invoice.getTaxes());



        return invoiceModel;
    }

//    public static InvoiceModel convertInvoiceModelToInvoice(InvoiceModel model) {
//        Invoice invoice = new Invoice();
//        List<InvoiceTax> taxes = new ArrayList<>();
//        List<InvoiceDiscount> discounts = new ArrayList<>();
//        List<InvoiceDescription> descriptions = new ArrayList<>();
//        for (InvoiceTax fetchedTax : taxes) {
//            InvoiceTax tax = new InvoiceTax();
//            tax.setTaxName(fetchedTax.getTaxName());
//            tax.setTaxRate(fetchedTax.getTaxRate());
//            taxes.add(tax);
//        }
//        invoice.setTaxes(taxes);
//
//        for (InvoiceDiscount fetchedDiscount : discounts) {
//            InvoiceDiscount discount = new InvoiceDiscount();
//            discount.setDiscountName(fetchedDiscount.getDiscountName());
//            discount.setDiscountRate(fetchedDiscount.getDiscountRate());
//            discounts.add(discount);
//        }
//        invoice.setDiscounts(discounts);
//
//        return invoice;
//    }
}
