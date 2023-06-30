package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    public boolean addInvoice(Invoice invoice) {
        return true;
    }

    public boolean editInvoice(Invoice invoice) {
        return true;
    }


    public List<Invoice> getInvoice(Long invoiceId) {
        List<Invoice> invoices = new ArrayList<>();

        return invoices;
    }

    public List<Invoice> getAllInvoices(Invoice invoice) {
        List<Invoice> invoices = new ArrayList<>();

        return invoices;
    }
}
