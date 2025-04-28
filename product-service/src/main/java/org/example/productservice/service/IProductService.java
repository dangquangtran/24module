package org.example.productservice.service;

import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.entity.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductService {
    List<Product> getAllProducts();
    CompletableFuture<Product> getProductById(Long id);
    Product createProduct(CreateProductDTO dto);
    Product updateProduct(Long id, UpdateProductDTO dto);
    void deleteProduct(Long id);
}
