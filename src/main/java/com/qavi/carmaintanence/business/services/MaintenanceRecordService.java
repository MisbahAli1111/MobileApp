package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceRecordService {

    @Autowired
    MaintenanceRecordRepository maintenanceRecordRepository;

    public boolean addRecord(MaintenanceRecord maintenanceRecord)
    {
        return true;
    }
}
