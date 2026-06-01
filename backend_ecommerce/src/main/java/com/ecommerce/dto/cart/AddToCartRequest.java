package com.ecommerce.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToCartRequest {

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    @NotNull(message = "Product Id is required")
    private Long productId;

    @Min(
            value = 1,
            message = "Quantity must be at least 1"
    )
    private Integer quantity;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(
            Long customerId
    ) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(
            Long productId
    ) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity
    ) {
        this.quantity = quantity;
    }
}