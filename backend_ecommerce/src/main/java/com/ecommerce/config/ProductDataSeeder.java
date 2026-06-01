package com.ecommerce.config;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.enums.ProductCategory;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDataSeeder
        implements CommandLineRunner {

    private final ProductRepository
            productRepository;

    @Override
    public void run(String... args) {

        if (productRepository.count() > 0) {
            return;
        }

        List<Product> products =
                List.of(

                        Product.builder()
                                .name("iPhone 16")
                                .description(
                                        "Apple smartphone"
                                )
                                .price(
                                        new BigDecimal(
                                                "89999"
                                        )
                                )
                                .stockQuantity(10)
                                .category(
                                        ProductCategory.ELECTRONICS
                                )
                                .imageUrl(null)
                                .build(),

                        Product.builder()
                                .name("Nike Shoes")
                                .description(
                                        "Running shoes"
                                )
                                .price(
                                        new BigDecimal(
                                                "4999"
                                        )
                                )
                                .stockQuantity(20)
                                .category(
                                        ProductCategory.FOOTWEAR
                                )
                                .imageUrl(null)
                                .build(),

                        Product.builder()
                                .name("Laptop")
                                .description(
                                        "Gaming laptop"
                                )
                                .price(
                                        new BigDecimal(
                                                "75999"
                                        )
                                )
                                .stockQuantity(5)
                                .category(
                                        ProductCategory.ELECTRONICS
                                )
                                .imageUrl(null)
                                .build(),

                        Product.builder()
                                .name("Atomic Habits")
                                .description(
                                        "Self-help book"
                                )
                                .price(
                                        new BigDecimal(
                                                "599"
                                        )
                                )
                                .stockQuantity(15)
                                .category(
                                        ProductCategory.BOOKS
                                )
                                .imageUrl(null)
                                .build(),

                        Product.builder()
                                .name("Headphones")
                                .description(
                                        "Wireless headphones"
                                )
                                .price(
                                        new BigDecimal(
                                                "2999"
                                        )
                                )
                                .stockQuantity(12)
                                .category(
                                        ProductCategory.ELECTRONICS
                                )
                                .imageUrl(null)
                                .build()
                );

        productRepository.saveAll(
                products
        );

        System.out.println(
                "Dummy products inserted"
        );
    }
}