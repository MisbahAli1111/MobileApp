package com.qavi.carmaintanence.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String discountName;

    private Double discountRate;

    @ManyToOne
    private Invoice invoice;
}
