package com.qavi.carmaintanence.business.services;

import com.qavi.carmaintanence.business.entities.VehicleMedia;
import com.qavi.carmaintanence.business.repositories.VehicleMediaRepository;
import com.qavi.carmaintanence.business.repositories.VehicleRepository;
import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleMediaService {
    @Autowired
    VehicleMediaRepository vehicleMediaRepository;

    public Long saveFileKey(String uploadedFileKey) {
        VehicleMedia image = VehicleMedia.builder().key(uploadedFileKey).build();
        VehicleMedia savedImg = vehicleMediaRepository.save(image);
        return savedImg.getId();
    }
}
