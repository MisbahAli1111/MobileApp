package com.qavi.carmaintanence.business.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountModel {
    private String discountName;
    private Double discountRate;
}
