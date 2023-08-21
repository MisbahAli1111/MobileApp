package com.qavi.carmaintanence.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceTax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String taxName;

    private String taxRate;

    @ManyToOne
    private Invoice invoice;
}
