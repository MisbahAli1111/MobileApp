package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class invoiceRepositoryTest {
  @Autowired
  InvoiceRepository invoicerepository;

}