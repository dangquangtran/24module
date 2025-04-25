package org.example.productservice.service;

import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.dto.product.UpdateProductDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductService {
    List<ProductVM> getAllProducts();
    CompletableFuture<ProductVM> getProductById(Long id);
    ProductVM createProduct(CreateProductDTO dto);
    ProductVM updateProduct(Long id, UpdateProductDTO dto);
    void deleteProduct(Long id);
}
