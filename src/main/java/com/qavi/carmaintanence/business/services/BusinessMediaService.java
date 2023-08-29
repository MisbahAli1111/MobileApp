package com.qavi.carmaintanence.business.services;


import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.BusinessMedia;
import com.qavi.carmaintanence.business.repositories.BusinessMediaRepository;
import com.qavi.carmaintanence.usermanagement.entities.user.ProfileImage;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BusinessMediaService {
    @Autowired
    BusinessMediaRepository businessMediaRepository;

    @Autowired
    BusinessService businessService;

    @Autowired
    BusinessMediaService businessMediaService;

    public Long saveFileKey(String uploadedFileKey) {
        BusinessMedia image = BusinessMedia.builder().key(uploadedFileKey).build();
        BusinessMedia savedImg = businessMediaRepository.save(image);
        return savedImg.getId();
    }

    public Map<String,Object> getBusinessProfileImgData(Long businessId){

        Map<String,Object> data = new HashMap<>();

        Business business = businessService.getBusiness(businessId);

        if(business.getBusinessProfileImage()==null){
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
