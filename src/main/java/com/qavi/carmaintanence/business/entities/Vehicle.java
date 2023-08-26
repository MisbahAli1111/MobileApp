package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String type;
    String model;
    String make;
    String year;
    @Column(unique = true)
    String registrationNumber;
    String color;
    LocalDateTime dateCreated;
    @ManyToOne
    User carOwner;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Business> associatedToBusiness;
    double kilometerDriven;
    @OneToMany
    List<VehicleMedia> vehicleMedia;
}
