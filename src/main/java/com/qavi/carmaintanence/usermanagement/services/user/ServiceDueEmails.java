package com.qavi.carmaintanence.usermanagement.services.user;

import com.qavi.carmaintanence.business.repositories.MaintenanceRecordRepository;
import com.qavi.carmaintanence.business.services.MaintenanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServiceDueEmails {
    @Autowired
    MaintenanceRecordRepository maintenanceRecordRepository;

//    @Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(fixedRate = 0)
//    public void findServiceDue()
//    {
//        var serviceDue = maintenanceRecordRepository.getServiceDue();
//        System.out.println(serviceDue);
//
//    }
}
