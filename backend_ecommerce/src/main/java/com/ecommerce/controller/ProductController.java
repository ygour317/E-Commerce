package com.ecommerce.controller;

import com.ecommerce.dto.product.ProductResponse;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(
        origins =
                "http://localhost:4200"
)
public class ProductController {

    private final ProductService
            productService;

    @GetMapping
    public List<ProductResponse>
    getProducts() {

        return productService
                .getAllProducts();
    }
}