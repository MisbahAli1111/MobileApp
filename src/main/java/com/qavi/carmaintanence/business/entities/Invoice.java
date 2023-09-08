package com.qavi.carmaintanence.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @OneToOne
    private MaintenanceRecord maintenanceRecord;


    @OneToMany (cascade=CascadeType.ALL)
    private List<Item> descriptions;
    @OneToMany (cascade=CascadeType.ALL)
    private List<Tax> taxes;
    @OneToMany (cascade=CascadeType.ALL)
    private List<Discount> discounts;
    private Long maintainedById;
    private LocalDate invoiceDue;
    private boolean status;
    private boolean enabled;
    private Long vehicleId;
    private LocalDate date;
    private  Long total;
    @ManyToOne
    Business business;
}

