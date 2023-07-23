package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.models.InvoiceModel;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.models.UserDataModel;

public class InvoiceConverter {
    public static InvoiceModel convertInvoiceToInvoiceModel(Invoice invoice) {
        InvoiceModel invoiceModel= new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setDate(invoice.getDate());
        invoiceModel.setInvoiceDue(invoice.getInvoiceDue());
        invoiceModel.setDescription(invoice.getDescription());
        invoiceModel.setQty(invoice.getQty());
        invoiceModel.setRate(invoice.getRate());
        invoiceModel.setAmountWithDis(invoice.getAmountWithDis());
        invoiceModel.setDiscountRate(invoice.getDiscountRate());
        invoiceModel.setTotal(invoice.getTotal());
        invoiceModel.setDescription(invoice.getDescription());
        invoiceModel.setDiscountName(invoice.getDiscountName());
        invoiceModel.setTaxName(invoice.getTaxName());
        invoiceModel.setTaxRate(invoice.getTaxRate());


        return invoiceModel;
    }
}
