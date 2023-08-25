package com.qavi.carmaintanence.business.services;


import com.qavi.carmaintanence.business.entities.BusinessMedia;
import com.qavi.carmaintanence.business.repositories.BusinessMediaRepository;
import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessMediaService {
    @Autowired
    BusinessMediaRepository businessMediaRepository;

    public Long saveFileKey(String uploadedFileKey) {
        BusinessMedia image = BusinessMedia.builder().key(uploadedFileKey).build();
        BusinessMedia savedImg = businessMediaRepository.save(image);
        return savedImg.getId();
    }
}
