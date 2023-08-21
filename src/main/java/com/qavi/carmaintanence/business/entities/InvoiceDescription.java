package com.qavi.carmaintanence.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String item;

    private Double rate;

    private Double quantity;

    private Double amount;

    @ManyToOne
    private Invoice invoice;
}
