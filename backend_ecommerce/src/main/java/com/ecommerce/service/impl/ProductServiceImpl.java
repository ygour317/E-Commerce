package com.ecommerce.service.impl;

import com.ecommerce.dto.product.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl
        implements ProductService {

    private final ProductRepository
            productRepository;

    @Override
    public List<ProductResponse>
    getAllProducts() {

        List<Product> products =
                productRepository
                        .findByActiveTrue();

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponse
    mapToResponse(Product product) {

        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(
                        product.getDescription()
                )
                .price(
                        product.getPrice()
                )
                .imageUrl(
                        product.getImageUrl()
                )
                .stockQuantity(
                        product.getStockQuantity()
                )
                .category(
                        product.getCategory()
                )
                .build();
    }
}