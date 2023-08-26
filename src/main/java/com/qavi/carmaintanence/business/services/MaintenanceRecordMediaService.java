package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.MaintenanceRecordMedia;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceRecordMediaService {
    @Autowired
    MaintenanceRecordMediaRepository maintenanceRecordMediaRepository;

    public Long saveFileKey(String uploadedFileKey) {
        MaintenanceRecordMedia image = MaintenanceRecordMedia.builder().key(uploadedFileKey).build();
        MaintenanceRecordMedia savedImg = maintenanceRecordMediaRepository.save(image);
        return savedImg.getId();
    }
}
