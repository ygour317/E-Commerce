package com.ecommerce.service;

import com.ecommerce.dto.product.ProductRequest;
import com.ecommerce.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse addProduct(
            ProductRequest request
    );

    ProductResponse updateProduct(
            Long productId,
            ProductRequest request
    );

    void deleteProduct(
            Long productId
    );

    void reactivateProduct(
            Long productId
    );

    List<ProductResponse> getAllProductsForAdmin();
}