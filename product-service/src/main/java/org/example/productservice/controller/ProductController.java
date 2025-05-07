    package org.example.productservice.controller;

    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        @Operation(summary = "Get all products", description = "Returns a list of all products, including inactive ones.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products")
        })
        public ResponseEntity<ApiResponseWrapper<List<ProductVM>>> getAllProducts() {
            List<ProductVM> products = productHandler.getAllProducts();
            return ResponseEntity.ok(new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    products
            ));
        }

        @GetMapping("/active")
        @Operation(summary = "Get all active products", description = "Returns a list of products that are currently active.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of active products")
        })
        public ResponseEntity<ApiResponseWrapper<List<ProductVM>>> getAllActiveProducts() {
            List<ProductVM> products = productHandler.getAllActiveProducts();
            return ResponseEntity.ok(new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    products
            ));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get product by ID", description = "Returns a product by its ID.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
                @ApiResponse(responseCode = "404", description = "Product not found")
        })
        public ResponseEntity<ApiResponseWrapper<ProductVM>> getProductById(@PathVariable Long id) {
            ProductVM product = productHandler.getProductById(id);
            return ResponseEntity.ok(new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Product retrieved successfully",
                    product
            ));
        }

        @PostMapping
        @Operation(summary = "Create a new product", description = "Creates a new product. Only users with ADMIN role are authorized.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Product created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid product data"),
                @ApiResponse(responseCode = "403", description = "Access denied")
        })
        public ResponseEntity<ApiResponseWrapper<ProductVM>> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
            ProductVM createdProduct = productHandler.createProduct(createProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    "Product created successfully",
                    createdProduct
            ));
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update an existing product", description = "Updates the details of an existing product by ID. Only ADMIN users can perform this operation.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Product updated successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid update data"),
                @ApiResponse(responseCode = "403", description = "Access denied"),
                @ApiResponse(responseCode = "404", description = "Product not found")
        })
        public ResponseEntity<ApiResponseWrapper<ProductVM>> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDTO updateProductDTO) {
            ProductVM updatedProduct = productHandler.updateProduct(id, updateProductDTO);
            return ResponseEntity.ok(new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Product updated successfully",
                    updatedProduct
            ));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete a product", description = "Deletes a product by its ID. Only ADMIN users can perform this operation.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
                @ApiResponse(responseCode = "403", description = "Access denied"),
                @ApiResponse(responseCode = "404", description = "Product not found")
        })
        public ResponseEntity<ApiResponseWrapper<String>> deleteProduct(@PathVariable Long id) {
            productHandler.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Product deleted successfully",
                    null
            ));
        }
    }