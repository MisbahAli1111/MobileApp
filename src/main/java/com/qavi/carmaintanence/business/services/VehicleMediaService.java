package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.Vehicle;
import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String,Object> getVehicleProfileImgData(Long vehicleId){

        Map<String,Object> data = new HashMap<>();
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        if(vehicle==null){
            data.put("id",null);
            data.put("url",null);
        }
        else{
            data.put("id",business.getBusinessProfileImage().getId());
            data.put("url" , "/api/file/" + business.getBusinessProfileImage().getKey());
        }
        return data;
    }
}
