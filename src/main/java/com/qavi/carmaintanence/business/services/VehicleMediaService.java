package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VehicleMediaService {
    @Autowired
    VehicleMediaRepository vehicleMediaRepository;

    @Autowired
    VehicleService vehicleService;

    public Long saveFileKey(String uploadedFileKey) {
        VehicleMedia image = VehicleMedia.builder().key(uploadedFileKey).build();
        VehicleMedia savedImg = vehicleMediaRepository.save(image);
        return savedImg.getId();
    }

    public List<Map<String, Object>> getVehicleImagesData(Long Id) {
        List<Long> vehicleMediaIds = vehicleMediaRepository.getVehicleMediaIdsByVehicleId(Id);
        System.out.println(vehicleMediaIds);
        List<VehicleMedia> vehicleMediaData = vehicleMediaRepository.getVehicleMediaDataByIds(vehicleMediaIds);
        System.out.println(vehicleMediaData);
        List<Map<String, Object>> resultMapList = new ArrayList<>();

        if (vehicleMediaData == null) {
            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("id", null);
            resultMap.put("url", null);
        } else {
            for (VehicleMedia vehicleMedia : vehicleMediaData) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("id", vehicleMedia.getId());
                resultMap.put("url", "/api/file/" + vehicleMedia.getKey());
                resultMapList.add(resultMap);
            }
        }

        return resultMapList;
    }
}


