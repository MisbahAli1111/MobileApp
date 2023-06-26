package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private Long vehicleId;

    private DateTime date;

    private DateTime invoiceDue;

    private Long subTotal;

    private boolean status;

    private String description;
    private double rate;
    private double qty;
    private Long amount;

    private String taxName;
    private double taxRate;

    private String discountName;
    private double discountRate;

    private Long balance;

}

