package com.qavi.carmaintanence.usermanagement.entities.user;

import lombok.*;

import javax.persistence.Entity;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdate {

    private String oldPassword;
    private String newPassword;

}
