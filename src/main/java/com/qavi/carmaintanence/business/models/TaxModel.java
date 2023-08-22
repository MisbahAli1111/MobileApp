package com.qavi.carmaintanence.business.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxModel {
    private String taxName;

    private Double taxRate;

}
