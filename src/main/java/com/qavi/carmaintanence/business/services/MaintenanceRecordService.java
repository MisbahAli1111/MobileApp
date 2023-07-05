package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.globalexceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MaintenanceRecordService {

    @Autowired
    MaintenanceRecordRepository maintenanceRecordRepository;

    public boolean addRecord(MaintenanceRecord maintenanceRecord)
    {
        maintenanceRecordRepository.save(maintenanceRecord);

        return true;
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
            maintenanceRecord.setService(maintenanceRecord.getService());
        }

        //Kilometers
        if(Objects.nonNull(maintenanceRecord.getKilometerDriven()) &&
                !"".equals(maintenanceRecord.getKilometerDriven()))
        {
            maintenanceRecord.setKilometerDriven(maintenanceRecord.getKilometerDriven());
        }


        //MaintenanceDetails
        if(Objects.nonNull(maintenanceRecord.getMaintanenceDetail()) &&
                !"".equals(maintenanceRecord.getMaintanenceDetail()))
        {
            maintenanceRecord.setMaintanenceDetail(maintenanceRecord.getMaintanenceDetail());
        }

        if(foundRecord != null)
        {
            maintenanceRecordRepository.save(maintenanceRecord);
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
