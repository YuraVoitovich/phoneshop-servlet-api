package com.es.phoneshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private List<PriceHistory> priceHistoryList;
    private Currency currency;
    private int stock;
    private String imageUrl;


    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistoryList = new ArrayList<>();
        LocalDate ld = LocalDate.now();
        this.priceHistoryList.add(new PriceHistory(this.price, ld));
        this.priceHistoryList.add(new PriceHistory(this.price.subtract(new BigDecimal(30)), ld.minusDays(10)));
        this.priceHistoryList.add(new PriceHistory(this.price.subtract(new BigDecimal(50)), ld.minusDays(30)));
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this(code, description, price, currency, stock, imageUrl);
        this.id = id;
    }
}