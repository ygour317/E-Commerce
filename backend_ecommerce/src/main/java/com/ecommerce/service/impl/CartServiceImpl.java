package com.ecommerce.service.impl;

import com.ecommerce.dto.cart.*;
import com.ecommerce.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl
        implements CartService {

    private final CartRepository
            cartRepository;

    private final CustomerRepository
            customerRepository;

    private final ProductRepository
            productRepository;

    private final CartItemRepository
            cartItemRepository;

    @Override
    public CartResponse addToCart(
            AddToCartRequest request
    ) {

        Cart cart =
                getOrCreateCart(
                        request.getCustomerId()
                );

        Product product =
                productRepository
                        .findById(
                                request.getProductId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Product not found"
                                )
                        );

        Optional<CartItem> existingItem =
                cart.getItems()
                        .stream()
                        .filter(item ->
                                item.getProduct()
                                        .getId()
                                        .equals(
                                                product.getId()
                                        )
                        )
                        .findFirst();

        if (existingItem.isPresent()) {

            CartItem item =
                    existingItem.get();

            item.setQuantity(
                    item.getQuantity()
                            + request.getQuantity()
            );

        } else {

            CartItem item =
                    CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(
                                    request.getQuantity()
                            )
                            .build();

            cart.getItems().add(item);
        }

        cartRepository.save(cart);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse increaseQuantity(
            UpdateCartRequest request
    ) {

        Cart cart =
                getCartEntity(
                        request.getCustomerId()
                );

        CartItem item =
                findCartItem(
                        cart,
                        request.getProductId()
                );

        item.setQuantity(
                item.getQuantity() + 1
        );

        cartRepository.save(cart);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse decreaseQuantity(
            UpdateCartRequest request
    ) {

        Cart cart =
                getCartEntity(
                        request.getCustomerId()
                );

        CartItem item =
                findCartItem(
                        cart,
                        request.getProductId()
                );

        if (item.getQuantity() == 1) {

            cart.getItems().remove(item);

        } else {

            item.setQuantity(
                    item.getQuantity() - 1
            );
        }

        cartRepository.save(cart);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse getCart(
            Long customerId
    ) {

        Cart cart =
                getOrCreateCart(customerId);

        return buildCartResponse(cart);
    }

    private Cart getOrCreateCart(
            Long customerId
    ) {

        return cartRepository
                .findByCustomerId(
                        customerId
                )
                .orElseGet(() -> {

                    Customer customer =
                            customerRepository
                                    .findById(customerId)
                                    .orElseThrow(
                                            () -> new RuntimeException(
                                                    "Customer not found"
                                            )
                                    );

                    Cart cart =
                            Cart.builder()
                                    .customer(customer)
                                    .items(
                                            new ArrayList<>()
                                    )
                                    .build();

                    return cartRepository
                            .save(cart);
                });
    }

    private Cart getCartEntity(
            Long customerId
    ) {

        return cartRepository
                .findByCustomerId(
                        customerId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Cart not found"
                        )
                );
    }

    private CartItem findCartItem(
            Cart cart,
            Long productId
    ) {

        return cart.getItems()
                .stream()
                .filter(item ->
                        item.getProduct()
                                .getId()
                                .equals(productId)
                )
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Product not found in cart"
                        )
                );
    }

    private CartResponse buildCartResponse(
            Cart cart
    ) {

        BigDecimal total =
                BigDecimal.ZERO;

        var responses =
                cart.getItems()
                        .stream()
                        .map(item -> {

                            BigDecimal itemPrice =
                                    item.getProduct()
                                            .getPrice()
                                            .multiply(
                                                    BigDecimal.valueOf(
                                                            item.getQuantity()
                                                    )
                                            );

                            return CartItemResponse
                                    .builder()
                                    .productId(
                                            item.getProduct()
                                                    .getId()
                                    )
                                    .productName(
                                            item.getProduct()
                                                    .getName()
                                    )
                                    .imageUrl(
                                            item.getProduct()
                                                    .getImageUrl()
                                    )
                                    .price(
                                            item.getProduct()
                                                    .getPrice()
                                    )
                                    .quantity(
                                            item.getQuantity()
                                    )
                                    .build();
                        })
                        .toList();

        for (CartItem item :
                cart.getItems()) {

            total =
                    total.add(
                            item.getProduct()
                                    .getPrice()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.getQuantity()
                                            )
                                    )
                    );
        }

        return CartResponse
                .builder()
                .customerId(
                        cart.getCustomer()
                                .getId()
                )
                .items(responses)
                .totalAmount(total)
                .build();
    }
}