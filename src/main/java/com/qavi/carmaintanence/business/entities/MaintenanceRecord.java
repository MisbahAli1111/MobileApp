package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class MaintenanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime maintanenceDateTime;
    @ManyToOne
    User maintainedBy;
    double kilometerDriven;
    String service;
   @OneToMany
    List<VehicleMedia> vehicleMedia;
    String maintanenceDetail;
    @ManyToOne
    Vehicle vehicle;

    @OneToMany
    List<MaintenanceRecordMedia> maintenanceRecordMedia;

}
