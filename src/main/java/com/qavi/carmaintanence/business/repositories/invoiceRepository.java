package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface invoiceRepository extends JpaRepository<Invoice,Long> {

    @Modifying
    @Query(value = "INSERT INTO invoice_business (business_id, invoice_id) VALUES (:businessId, :invoiceId)", nativeQuery = true)
     void insertInvoiceBusiness(@Param("businessId") Long businessId, @Param("invoiceId") Long invoiceId);


    List<Invoice> findAllByBusinessIdAndEnabledIsTrue(Long businessId);
//
//    List<Invoice> findAllInvoices();

    @Query(value = "select i.amount_with_dis, i.amount_with_out_dis  from invoice i where id =?1" , nativeQuery = true)
    Collection<?> findByMaintainedRecordId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Invoice v SET v.enabled = false WHERE v.id = :invoiceId")
    void UpdateInvoiceEnabled(@Param("invoiceId") Long invoiceId);

    @Query("SELECT m FROM Invoice m WHERE m.invoiceDue = :tomorrow")
    List<Invoice> findByInvoiceDueBetween(
            @Param("tomorrow")LocalDate tomorrow
            );

    @Query("SELECT i FROM Invoice i " +
            "WHERE i.date >= :startDate AND i.date <= :endDate " +
            "AND i.business.id = :businessId")
    List<Invoice> findReportDetails(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("businessId") Long businessId);

    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.status = 'false' AND i.business.id = :businessId")
    Long countInvoiceDueOfBusiness(@Param("businessId") Long businessId);


}
