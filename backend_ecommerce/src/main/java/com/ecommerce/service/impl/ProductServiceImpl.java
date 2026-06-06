package com.ecommerce.service.impl;

import com.ecommerce.dto.product.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.product.ProductRequest;

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

    @Override
    public ProductResponse addProduct(
            ProductRequest request
    ) {

        Product product =
                Product.builder()

                        .name(
                                request.getName()
                        )

                        .description(
                                request.getDescription()
                        )

                        .price(
                                request.getPrice()
                        )

                        .imageUrl(
                                request.getImageUrl()
                        )

                        .stockQuantity(
                                request.getStockQuantity()
                        )

                        .category(
                                request.getCategory()
                        )

                        .active(true)

                        .build();

        Product savedProduct =
                productRepository
                        .save(product);

        return mapToResponse(
                savedProduct
        );
    }

    @Override
    public ProductResponse updateProduct(

            Long productId,

            ProductRequest request
    ) {

        Product product =
                productRepository
                        .findById(
                                productId
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Product not found"
                                        )
                        );

        product.setName(
                request.getName()
        );

        product.setDescription(
                request.getDescription()
        );

        product.setPrice(
                request.getPrice()
        );

        product.setImageUrl(
                request.getImageUrl()
        );

        product.setStockQuantity(
                request.getStockQuantity()
        );

        product.setCategory(
                request.getCategory()
        );

        Product updatedProduct =
                productRepository
                        .save(product);

        return mapToResponse(
                updatedProduct
        );
    }

    @Override
    public void deleteProduct(
            Long productId
    ) {

        Product product =
                productRepository
                        .findById(
                                productId
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Product not found"
                                        )
                        );

        product.setActive(
                false
        );

        productRepository
                .save(
                        product
                );
    }

    @Override
    public void reactivateProduct(
            Long productId
    ) {

        Product product =
                productRepository
                        .findById(
                                productId
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Product not found"
                                        )
                        );

        product.setActive(
                true
        );

        productRepository.save(
                product
        );
    }

    @Override
    public List<ProductResponse> getAllProductsForAdmin() {

        return productRepository
                .findAll()
                .stream()
                .map(
                        this::mapToResponse
                )
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
                .active(
                        product.getActive()
                )
                .build();
    }
}