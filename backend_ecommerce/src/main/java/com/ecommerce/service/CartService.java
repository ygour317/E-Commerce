package com.ecommerce.service;

import com.ecommerce.dto.cart.*;

public interface CartService {

    CartResponse addToCart(
            AddToCartRequest request
    );

    CartResponse increaseQuantity(
            UpdateCartRequest request
    );

    CartResponse decreaseQuantity(
            UpdateCartRequest request
    );

    CartResponse getCart(
            Long customerId
    );
}