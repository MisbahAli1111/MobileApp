package com.qavi.carmaintanence.business.models;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor

public class InvoiceModel {
    Long id;



    private Long maintainedById;

    private LocalDate invoiceDue;


    private boolean status;



    private Long vehicleId;

    private LocalDateTime date;

    private  Long total;

}
