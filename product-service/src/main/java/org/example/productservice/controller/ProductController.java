package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.service.IProductService;
import org.example.productservice.common.response.ApiResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<ProductVM>>> getAllProducts() {
        List<ProductVM> products = productService.getAllProducts();
        ApiResponseWrapper<List<ProductVM>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Products retrieved successfully",
                products
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<ProductVM>> getProductById(@PathVariable Long id) {
        ProductVM product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product retrieved successfully",
                product
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<ProductVM>> createProduct(@Validated @RequestBody CreateProductDTO createProductDTO) {
        ProductVM createdProduct = productService.createProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseWrapper<>(
                        HttpStatus.CREATED.value(),
                        "Product created successfully",
                        createdProduct
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<ProductVM>> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDTO updateProductDTO) {
        ProductVM updatedProduct = productService.updateProduct(id, updateProductDTO);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product updated successfully",
                updatedProduct
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product deleted successfully",
                null
        ));
    }
}

