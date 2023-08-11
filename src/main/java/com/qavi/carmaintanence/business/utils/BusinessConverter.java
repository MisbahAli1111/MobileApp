package com.qavi.carmaintanence.business.utils;

import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.business.entities.Invoice;
import com.qavi.carmaintanence.business.models.BusinessModel;
import com.qavi.carmaintanence.business.models.InvoiceModel;

public class BusinessConverter {
    public static BusinessModel convertBusinessToBusinessModel(Business business) {
        BusinessModel businessModel= new BusinessModel();
        businessModel.setId(business.getId());
        businessModel.setBusinessAddress(business.getBusinessAddress());
        businessModel.setBusinessCity(business.getBusinessCity());
        businessModel.setBusinessCountry(business.getBusinessCountry());
        businessModel.setBusinessEmail(business.getBusinessEmail());
        businessModel.setBusinessName(business.getBusinessName());
        businessModel.setBusinessPhoneNumber(business.getBusinessPhoneNumber());
//        businessModel.s(business.getDiscountRate());
//        businessModel.setTotal(business.getTotal());
//        businessModel.setDescription(business.getDescription());
//        businessModel.setDiscountName(business.getDiscountName());
//        businessModel.setTaxName(business.getTaxName());
//        businessModel.setTaxRate(business.getTaxRate());


        return businessModel;
    }
}
