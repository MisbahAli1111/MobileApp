package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MaintenanceRecordService {

    @Autowired
    MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    public boolean addRecord(MaintanenceRecordModel maintanenceRecordModel,Long userId)
    {
        Optional<Vehicle> vehicle = vehicleRepository.findByRegistrationNumber(maintanenceRecordModel.getRegistrationNumber());
        if (vehicle.isPresent()) {
            Optional<User> owner = userRepository.findById(userId);
            MaintenanceRecord maintanenceRecord = new MaintenanceRecord();
            maintanenceRecord.setService(maintanenceRecordModel.getService());
            maintanenceRecord.setMaintanenceDetail(maintanenceRecordModel.getMaintanenceDetail());
            maintanenceRecord.setKilometerDriven(maintanenceRecordModel.getKilometerDriven());
            maintanenceRecord.setMaintanenceDateTime(LocalDateTime.now());
            maintanenceRecord.setMaintainedBy(owner.get());
            maintanenceRecord.setVehicle(vehicle.get());
            maintenanceRecordRepository.save(maintanenceRecord);
            return true;
        }else {
            throw new RecordNotFoundException("Vehicle not found");

        }
    }

    public List<MaintenanceRecord> getallrecords() {
        List<MaintenanceRecord> myRecords= maintenanceRecordRepository.findAll();
        return myRecords;
    }

    public Optional<MaintenanceRecord> getMaintenanceRecordById(Long Id) {
        Optional<MaintenanceRecord> myRecords =maintenanceRecordRepository.findById(Id);

        return myRecords;
    }

    public boolean editMaintenanceRecord(MaintenanceRecord maintenanceRecord,Long Id) {
        MaintenanceRecord foundRecord =maintenanceRecordRepository.findById(Id).get();
        //Service
        if(Objects.nonNull(maintenanceRecord.getService()) &&
                !"".equals(maintenanceRecord.getService()))
        {
            foundRecord.setService(maintenanceRecord.getService());
        }
        if(Objects.nonNull(maintenanceRecord.getMaintanenceDateTime()) &&
                !"".equals(maintenanceRecord.getMaintanenceDateTime()))
        {
            foundRecord.setMaintanenceDateTime(maintenanceRecord.getMaintanenceDateTime());
        }


        //Kilometers
        if(Objects.nonNull(maintenanceRecord.getKilometerDriven()) &&
                !"".equals(maintenanceRecord.getKilometerDriven()))
        {
            foundRecord.setKilometerDriven(maintenanceRecord.getKilometerDriven());
        }


        //MaintenanceDetails
        if(Objects.nonNull(maintenanceRecord.getMaintanenceDetail()) &&
                !"".equals(maintenanceRecord.getMaintanenceDetail()))
        {
            foundRecord.setMaintanenceDetail(maintenanceRecord.getMaintanenceDetail());
        }

        if(foundRecord != null)
        {
            maintenanceRecordRepository.save(foundRecord);
            return true;
        }
        else {
            return false;
        }

    }

    public boolean deleteRecord(Long id) {
        try {
            Optional<MaintenanceRecord> record = maintenanceRecordRepository.findById(id);
            if (record.isPresent()) {
                maintenanceRecordRepository.deleteById(id);
                return true;
            } else {
                throw new RecordNotFoundException("Record not found");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
