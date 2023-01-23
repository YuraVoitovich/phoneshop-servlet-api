package com.es.phoneshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PriceHistory implements Serializable {
    private BigDecimal price;
    private LocalDate date;
}
