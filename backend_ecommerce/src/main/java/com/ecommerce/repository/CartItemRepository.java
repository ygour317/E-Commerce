package com.ecommerce.repository;

import com.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    @Transactional
    void deleteByCartCustomer_IdAndProduct_Id(
            Long customerId,
            Long productId
    );
}