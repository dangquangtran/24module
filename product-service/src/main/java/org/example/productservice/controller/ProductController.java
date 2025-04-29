package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.productservice.common.response.ApiResponseWrapper;
import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.handler.IProductHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final IProductHandler productHandler;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<ProductVM>>> getAllProducts() {
        List<ProductVM> products = productHandler.getAllProducts();
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Products retrieved successfully",
                products
        ));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseWrapper<List<ProductVM>>> getAllActiveProducts() {
        List<ProductVM> products = productHandler.getAllActiveProducts();
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Products retrieved successfully",
                products
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<ProductVM>> getProductById(@PathVariable Long id) {
        ProductVM product = productHandler.getProductById(id);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product retrieved successfully",
                product
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<ProductVM>> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        ProductVM createdProduct = productHandler.createProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseWrapper<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                createdProduct
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<ProductVM>> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDTO updateProductDTO) {
        ProductVM updatedProduct = productHandler.updateProduct(id, updateProductDTO);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product updated successfully",
                updatedProduct
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<String>> deleteProduct(@PathVariable Long id) {
        productHandler.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product deleted successfully",
                null
        ));
    }
}