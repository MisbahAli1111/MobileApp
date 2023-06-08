package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class MaintenanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    DateTime maintanenceDateTime;
    @ManyToOne
    User maintainedBy;
    double kilometerDriven;
    String service;
    @OneToMany
    List<VehicleMedia> vehicleMedia;
    String maintanenceDetail;
    @ManyToOne
    Vehicle vehicle;
}
