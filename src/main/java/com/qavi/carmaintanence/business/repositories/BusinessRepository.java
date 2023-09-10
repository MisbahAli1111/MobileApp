package com.qavi.carmaintanence.business.repositories;

import com.qavi.carmaintanence.business.entities.Business;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BusinessRepository extends JpaRepository<Business,Long> {
    List<Business> findAllByOwnerId(Long id);


}
