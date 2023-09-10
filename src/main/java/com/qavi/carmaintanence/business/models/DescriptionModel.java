package com.qavi.carmaintanence.business.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DescriptionModel {
    private String item;
    private Double rate;
    private Double quantity;
    private Double amount;
}
