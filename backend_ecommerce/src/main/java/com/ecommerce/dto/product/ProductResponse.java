package com.ecommerce.dto.product;

import com.ecommerce.entity.enums.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Integer stockQuantity;

    private ProductCategory category;
}