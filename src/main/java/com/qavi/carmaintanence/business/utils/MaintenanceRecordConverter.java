package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;

public class MaintenanceRecordConverter {
public  static  MaintanenceRecordModel covertMRtoMRmodel(MaintenanceRecord maintenanceRecord)
{
    MaintanenceRecordModel maintanenceRecordModel= new MaintanenceRecordModel();
    maintanenceRecordModel.setId(maintenanceRecord.getId());
    maintanenceRecordModel.setMaintanenceDetail(maintenanceRecord.getMaintanenceDetail());
    maintanenceRecordModel.setMaintanenceDateTime(maintenanceRecord.getMaintanenceDateTime());
    maintanenceRecordModel.setService(maintenanceRecord.getService());
    maintanenceRecordModel.setKilometerDriven(maintenanceRecord.getKilometerDriven());
    maintanenceRecordModel.setMaintainedById(maintenanceRecord.getMaintainedBy().getId());
    String fullName = maintenanceRecord.getMaintainedBy().getFirstName() + " " + maintenanceRecord.getMaintainedBy().getLastName();
    maintanenceRecordModel.setName(fullName);
    return maintanenceRecordModel;
}
}
