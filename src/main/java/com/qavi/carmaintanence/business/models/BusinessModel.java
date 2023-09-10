package com.qavi.carmaintanence.business.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
public class BusinessModel {

    private long id;
    private String businessName;
    private String businessAddress;
    private String businessPhoneNumber;
    private String businessEmail;
    private String businessCountry;
    private String businessCity;
}
