package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.*;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;
import com.qavi.carmaintanence.business.repositories.BusinessRepository;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordMediaRepository;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import com.qavi.carmaintanence.usermanagement.repositories.UserRepository;
import com.qavi.carmaintanence.usermanagement.services.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class MaintenanceRecordService {

    @Autowired
    EmailService emailService;
    @Autowired
    MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    MaintenanceRecordMediaRepository maintenanceRecordMediaRepository;

    public Long addRecord(MaintanenceRecordModel maintanenceRecordModel,Long businessId,Long userId)
    {
        Optional<Vehicle> vehicle = vehicleRepository.findByRegistrationNumber(maintanenceRecordModel.getRegistrationNumber());

        if (vehicle.isPresent()) {
            Optional<User> owner = userRepository.findById(userId);
            MaintenanceRecord maintanenceRecord = new MaintenanceRecord();
            maintanenceRecord.setBusiness(businessRepository.findById(businessId).get());
           maintanenceRecord.setService(maintanenceRecordModel.getService());
            maintanenceRecord.setMaintanenceDetail(maintanenceRecordModel.getMaintanenceDetail());
            maintanenceRecord.setKilometerDriven(maintanenceRecordModel.getKilometerDriven());
            maintanenceRecord.setMaintanenceDateTime(maintanenceRecordModel.getMaintanenceDateTime());
            maintanenceRecord.setServiceDue(maintanenceRecordModel.getServiceDue());
            maintanenceRecord.setMaintainedBy(owner.get());
            maintanenceRecord.setVehicle(vehicle.get());
            MaintenanceRecord maintenanceRecord = maintenanceRecordRepository.save(maintanenceRecord);
            return maintenanceRecord.getId();
        }else {
            throw new RecordNotFoundException("Vehicle not found");

        }
    }
    public String findRegistartionNumberFromRecords (Long recordId)
    {
        Optional<MaintenanceRecord> record = maintenanceRecordRepository.findById(recordId);
        if(record.isPresent())
        {
            MaintenanceRecord maintenanceRecord = record.get();
            Vehicle vehicle = maintenanceRecord.getVehicle();

            if (vehicle != null) {
                return vehicle.getRegistrationNumber();
            }else {
                return "Vehicle not associated with this maintenance record";
            }
        } else {
            return "Record not found";
        }
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void findServiceDue() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = currentDateTime.plusDays(1);
        LocalDateTime startOfTomorrow = tomorrow.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfTomorrow = tomorrow.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        System.out.println(currentDateTime);
        System.out.println(startOfTomorrow);
        System.out.println(endOfTomorrow);

        List<MaintenanceRecord> serviceDueRecords = maintenanceRecordRepository.findByServiceDueBetween(startOfTomorrow, endOfTomorrow);

        if (serviceDueRecords != null && !serviceDueRecords.isEmpty()) {
            List<String> emailList = new ArrayList<>();
            for (MaintenanceRecord record : serviceDueRecords) {
                String email = record.getVehicle().getCarOwner().getEmail();
                emailList.add(email);
            }
            String subject = "Service Due Notification";

            for (String recipient : emailList) {
                // Split the recipient email address using the dot as a delimiter
                String[] parts = recipient.split("\\@");

                // The first part (index 0) should be the first name
                String recipientFirstName = parts[0];
                System.out.println(recipientFirstName);

                // Include the recipient's first name in the email message
                String message = "Hi " + recipientFirstName + ",\n" +"Your vehicle might need servicing. We are available at your service.";

                emailService.serviceDueEmail(recipient, subject, message);
            }


        } else {
            System.out.println("No service due records found for tomorrow (" + startOfTomorrow + " - " + endOfTomorrow + ")" );
        }

    }

    public List<MaintenanceRecord> getallrecords(Long businessId) {
        List<MaintenanceRecord> myRecords= maintenanceRecordRepository.findAllByBusinessId(businessId);
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
        if(Objects.nonNull(maintenanceRecord.getMaintanenceDateTime()))
        {
            foundRecord.setMaintanenceDateTime(maintenanceRecord.getMaintanenceDateTime());
        }


        //Kilometers
        foundRecord.setKilometerDriven(maintenanceRecord.getKilometerDriven());


        //MaintenanceDetails
        if(Objects.nonNull(maintenanceRecord.getMaintanenceDetail()) &&
                !maintenanceRecord.getMaintanenceDetail().isEmpty())
        {
            foundRecord.setMaintanenceDetail(maintenanceRecord.getMaintanenceDetail());
        }

        maintenanceRecordRepository.save(foundRecord);
        return true;

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

    public String findTypeOfVehicle (String registrationNumber)
    {
        return vehicleRepository.getVehicleTypeFromRegNumber(registrationNumber);
    }

    public Long getOwnerId(Long Id)
    {
        Long id = maintenanceRecordRepository.getOwnerId(Id);
        return id;

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
