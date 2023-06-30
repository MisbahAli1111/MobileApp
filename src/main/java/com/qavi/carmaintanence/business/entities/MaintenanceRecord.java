package com.qavi.carmaintanence.business.entities;

import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ComponentScan
public class MaintenanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate maintanenceDateTime;
    Integer vid;

    @ManyToOne
    User maintainedBy;
    double kilometerDriven;
    private String description;

    @OneToMany
    List<VehicleMedia> vehicleMedia;
    String maintanenceDetail;
    @ManyToOne
    Vehicle vehicle;
}
