package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    @ManyToMany
    private List<User> user;

    private Long maintainedById;

    private LocalDate invoiceDue;


    private boolean status;



    private Long vehicleId;
    private LocalDateTime date;

    private String description;
    private double rate;
    private double qty;
    private Long amountWithOutDis;

    private String taxName;
    private double taxRate;

    private String discountName;
    private double discountRate;

    private Long amountWithDis;
    private  Long total;

}

