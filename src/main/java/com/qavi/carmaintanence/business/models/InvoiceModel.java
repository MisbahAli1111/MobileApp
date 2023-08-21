package com.qavi.carmaintanence.business.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    private List<Map<String,Object>> taxes;

    private List<Map<String,Object>> descriptions;

    private List<Map<String,Object>> discounts;

}
