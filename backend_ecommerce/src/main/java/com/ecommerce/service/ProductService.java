package com.ecommerce.service;

import com.ecommerce.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();
}