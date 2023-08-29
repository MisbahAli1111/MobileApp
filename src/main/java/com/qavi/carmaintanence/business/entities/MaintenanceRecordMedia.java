package com.qavi.carmaintanence.business.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRecordMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
}
