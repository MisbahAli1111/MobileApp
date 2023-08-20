package com.qavi.carmaintanence.business.models;

import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
public class MaintanenceRecordModel {
    Long id;
    LocalDateTime maintanenceDateTime;
    double kilometerDriven;
    String service;
    Long vehicleId;
    Long maintainedById;
    String maintanenceDetail;
    String registrationNumber;

}
