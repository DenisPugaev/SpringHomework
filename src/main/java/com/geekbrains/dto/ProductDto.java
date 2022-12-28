package com.geekbrains.dto;

import com.geekbrains.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String manufacturer;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.manufacturer = product.getManufacturer();
    }


}
