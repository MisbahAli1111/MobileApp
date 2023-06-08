package com.qavi.carmaintanence.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qavi.carmaintanence.usermanagement.entities.user.User;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String businessName;
    private String businessAddress;
    private String businessPhoneNumber;
    private String businessEmail;
    private String businessCountry;
    private String businessCity;
    private LocalDateTime businessRegisteredAt;
    private boolean enabled;
    @JsonIgnore
    @ManyToMany
    private List<User> customer;
    @ManyToOne
    private User owner;
    @JsonIgnore
    @OneToMany
    private List<User> employee;
}
