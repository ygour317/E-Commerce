package com.ecommerce.dto.cart;

import jakarta.validation.constraints.NotNull;

public class UpdateCartRequest {

    @NotNull(message = "Customer Id required")
    private Long customerId;

    @NotNull(message = "Product Id required")
    private Long productId;

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
}