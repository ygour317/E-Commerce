package com.ecommerce.controller;

import com.ecommerce.dto.product.ProductRequest;
import com.ecommerce.dto.product.ProductResponse;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        "/api/admin/products"
)
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://localhost:4200"
)
public class AdminProductController {

    private final ProductService
            productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct( @RequestBody ProductRequest request) {

        return productService.addProduct(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct( @PathVariable Long id, @RequestBody ProductRequest request) {

        return productService.updateProduct( id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct( @PathVariable Long id) {

        productService.deleteProduct(id);
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivateProduct(@PathVariable Long id) {

        productService.reactivateProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ProductResponse>
    getAllProducts() {

        return productService.getAllProductsForAdmin();
    }

}