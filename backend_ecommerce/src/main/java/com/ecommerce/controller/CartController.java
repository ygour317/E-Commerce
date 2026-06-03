package com.ecommerce.controller;

import com.ecommerce.dto.cart.*;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(
        origins =
                "http://localhost:4200"
)
public class CartController {

    private final CartService
            cartService;

    @PostMapping("/add")
    public CartResponse addToCart(
            @Valid
            @RequestBody
            AddToCartRequest request
    ) {

        return cartService
                .addToCart(request);
    }

    @PutMapping("/increase")
    public CartResponse increase(
            @Valid
            @RequestBody
            UpdateCartRequest request
    ) {

        return cartService
                .increaseQuantity(request);
    }

    @PutMapping("/decrease")
    public CartResponse decrease(
            @Valid
            @RequestBody
            UpdateCartRequest request
    ) {

        return cartService
                .decreaseQuantity(request);
    }

    @GetMapping("/{customerId}")
    public CartResponse getCart(
            @PathVariable
            Long customerId
    ) {

        return cartService
                .getCart(customerId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItem(

            @RequestParam Long customerId,

            @RequestParam Long productId
    ) {

        cartService.removeItem(
                customerId,
                productId
        );

        return ResponseEntity.ok()
                .build();
    }

}