package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.entities.MaintenanceRecordMedia;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.repositories.MaintenanceRecordMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaintenanceRecordMediaService {
    @Autowired
    MaintenanceRecordMediaRepository maintenanceRecordMediaRepository;

    @Autowired
    MaintenanceRecordService maintenanceRecordService;

    public Long saveFileKey(String uploadedFileKey) {
        MaintenanceRecordMedia image = MaintenanceRecordMedia.builder().key(uploadedFileKey).build();
        MaintenanceRecordMedia savedImg = maintenanceRecordMediaRepository.save(image);
        return savedImg.getId();
    }

    public List<Map<String, Object>> getVehicleImagesData(Long Id) {
        List<Long> recordMediaIds = maintenanceRecordMediaRepository.getRecordMediaIdsByRecordId(Id);
        List<MaintenanceRecordMedia> recordMediaData = maintenanceRecordMediaRepository.getRecordMediaDataByIds(recordMediaIds);
        List<Map<String, Object>> resultMapList = new ArrayList<>();

        if (recordMediaData == null) {
            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("id", null);
            resultMap.put("url", null);
        } else {
            for (MaintenanceRecordMedia recordMedia : recordMediaData) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("id", recordMedia.getId());
                resultMap.put("url", "/api/file/" + recordMedia.getKey());
                resultMapList.add(resultMap);
            }
        }

        return resultMapList;
    }
}
