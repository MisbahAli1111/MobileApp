package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.*;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordMediaRepository;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    @Autowired
    MaintenanceRecordMediaRepository maintenanceRecordMediaRepository;

    public Long addRecord(MaintanenceRecordModel maintanenceRecordModel,Long userId)
    {
        Optional<Vehicle> vehicle = vehicleRepository.findByRegistrationNumber(maintanenceRecordModel.getRegistrationNumber());
        if (vehicle.isPresent()) {
            Optional<User> owner = userRepository.findById(userId);
            MaintenanceRecord maintanenceRecord = new MaintenanceRecord();
            maintanenceRecord.setService(maintanenceRecordModel.getService());
            maintanenceRecord.setMaintanenceDetail(maintanenceRecordModel.getMaintanenceDetail());
            maintanenceRecord.setKilometerDriven(maintanenceRecordModel.getKilometerDriven());
            maintanenceRecord.setMaintanenceDateTime(maintanenceRecordModel.getMaintanenceDateTime());
            maintanenceRecord.setMaintainedBy(owner.get());
            maintanenceRecord.setVehicle(vehicle.get());
            MaintenanceRecord maintenanceRecord = maintenanceRecordRepository.save(maintanenceRecord);
            return maintenanceRecord.getId();
        }else {
            throw new RecordNotFoundException("Vehicle not found");

        }
    }

    public List<MaintenanceRecord> getallrecords() {
        List<MaintenanceRecord> myRecords= maintenanceRecordRepository.findAll();
        return myRecords;
    }

    public MaintenanceRecord getRecord(Long id) {
        return maintenanceRecordRepository.findById(id).get();
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

    public List<Map<String,Object>> findRegistrationNumber(Long businessId)
    {
        var users = vehicleRepository.findRegistrationNumberInBusiness(businessId);
        return users;
    }

    public void saveRecordImage(Long profileImgId, Long recordId) {
        MaintenanceRecordMedia savedImg = maintenanceRecordMediaRepository.findById(profileImgId).get();
        MaintenanceRecord maintenanceRecord = getRecord(recordId);
        List<MaintenanceRecordMedia> recordImages = maintenanceRecord.getMaintenanceRecordMedia();
        recordImages.add(savedImg);
        maintenanceRecord.setMaintenanceRecordMedia(recordImages);
        maintenanceRecordRepository.save(maintenanceRecord);
    }



}
