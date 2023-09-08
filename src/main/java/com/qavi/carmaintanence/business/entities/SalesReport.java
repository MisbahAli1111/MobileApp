package com.qavi.carmaintanence.business.entities;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String invoiceCustomer;
    private String invoiceMaintainedBy;
    private LocalDateTime invoiceCreated;
    private Long total;

}
