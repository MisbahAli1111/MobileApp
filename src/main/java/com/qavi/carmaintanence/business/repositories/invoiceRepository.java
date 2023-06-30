package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface invoiceRepository extends JpaRepository<Invoice,Long> {

}
