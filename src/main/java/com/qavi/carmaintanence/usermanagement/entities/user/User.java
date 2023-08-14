package com.qavi.carmaintanence.usermanagement.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qavi.carmaintanence.business.entities.Business;
import com.qavi.carmaintanence.usermanagement.entities.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")   //Because user is reserved keyword, JDBC not allowing to create table with the name "user"
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String cnicNumber;
    private String countryCode;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToOne(orphanRemoval = true)
    private ProfileImage profileImage;
    private String phoneNumber;
    @JsonIgnore

    private String authType;
    private boolean enabled;
    private String country;

    private String deviceId;
    private String address;
    private String fcmToken;
    private LocalDateTime lastLoginAt;
    private String appleIdentifier;
    private LocalDateTime registeredAt;
    private String city;
    private boolean emailNotificationEnabled;
//   @OneToMany
//   TODO
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "Role_Assigned",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> role;


    @OneToMany
    List<Business> ownBusiness;

    public User(String firstName, String lastName, String email)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

}