package com.qavi.carmaintanence.business.repositories;

import antlr.collections.List;
import com.qavi.carmaintanence.business.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface invoiceRepository extends JpaRepository<Invoice,Long> {

    @Query(value = "select i.amount_with_dis, i.amount_with_out_dis  from invoice i where id =?1" , nativeQuery = true)
    Collection<?> findByMaintainedRecordId(Long id);


}
