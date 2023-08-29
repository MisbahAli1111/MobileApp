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

    public List<Map<String, Object>> getVehicleProfileImgData(Long businessId,Long vehicleId) {
        List<Map<String, Object>> imagesData = new ArrayList<>();

        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        if (vehicle != null) {
            List<VehicleMedia> vehicleMediaList = vehicle.getVehicleMedia();

            for (VehicleMedia vehicleMedia : vehicleMediaList) {
                Map<String, Object> imageData = new HashMap<>();
                imageData.put("id", vehicleMedia.getId());
                imageData.put("url", "/api/file/" + vehicleMedia.getKey());
                imagesData.add(imageData);
            }
        }

        return imagesData;
    }

}
