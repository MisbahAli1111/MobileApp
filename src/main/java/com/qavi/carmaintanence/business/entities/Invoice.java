package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @OneToOne
    private MaintenanceRecord maintenanceRecord;

    @ManyToMany
    private List<User> user;

    private Long maintainedById;

    private LocalDate invoiceDue;


    private boolean status;



    private Long vehicleId;
    private LocalDate date;

    private String description;
    private double rate;
    private double qty;
    private Long amountWithOutDis;

    private String taxName;
    private double taxRate;

    private String discountName;
    private double discountRate;

    private Long amountWithDis;

}

