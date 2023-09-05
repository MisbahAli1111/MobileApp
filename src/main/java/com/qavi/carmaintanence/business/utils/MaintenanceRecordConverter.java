package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.MaintenanceRecord;
import com.qavi.carmaintanence.business.models.MaintanenceRecordModel;

import java.time.LocalDateTime;

public class MaintenanceRecordConverter {
public  static  MaintanenceRecordModel covertMRtoMRmodel(MaintenanceRecord maintenanceRecord)
{
    MaintanenceRecordModel maintanenceRecordModel= new MaintanenceRecordModel();
    maintanenceRecordModel.setId(maintenanceRecord.getId());
    maintanenceRecordModel.setMaintanenceDetail(maintenanceRecord.getMaintanenceDetail());
    maintanenceRecordModel.setMaintanenceDateTime(maintenanceRecord.getMaintanenceDateTime());
    maintanenceRecordModel.setServiceDue(maintenanceRecord.getServiceDue());
    maintanenceRecordModel.setService(maintenanceRecord.getService());
    maintanenceRecordModel.setKilometerDriven(maintenanceRecord.getKilometerDriven());
    maintanenceRecordModel.setMaintainedById(maintenanceRecord.getMaintainedBy().getId());
    maintanenceRecordModel.setVehicleOwner(maintenanceRecord.getVehicle().getCarOwner().getFirstName()+ "" +maintenanceRecord.getVehicle().getCarOwner().getLastName());
    maintanenceRecordModel.setVehicleId(maintenanceRecord.getVehicle().getId());
    maintanenceRecordModel.setRegistrationNumber(maintenanceRecord.getVehicle().getRegistrationNumber());
    maintanenceRecordModel.setType(maintenanceRecord.getVehicle().getType());
    maintanenceRecordModel.setParentCompany(maintenanceRecord.getVehicle().getParentCompany());


    String fullName = maintenanceRecord.getMaintainedBy().getFirstName()+ "" +maintenanceRecord.getMaintainedBy().getLastName();
    maintanenceRecordModel.setName(fullName);
    return maintanenceRecordModel;
}
}
