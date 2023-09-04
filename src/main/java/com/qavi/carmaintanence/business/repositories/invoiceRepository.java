package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface invoiceRepository extends JpaRepository<Invoice,Long> {

    List<Invoice> findAllByEnabledIsTrue();

    @Query(value = "select i.amount_with_dis, i.amount_with_out_dis  from invoice i where id =?1" , nativeQuery = true)
    Collection<?> findByMaintainedRecordId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Invoice v SET v.enabled = false WHERE v.id = :invoiceId")
    void UpdateInvoiceEnabled(@Param("invoiceId") Long invoiceId);

}
