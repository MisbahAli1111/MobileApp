package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
