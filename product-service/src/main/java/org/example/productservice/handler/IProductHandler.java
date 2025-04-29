package org.example.productservice.handler;

import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.dto.product.UpdateProductDTO;

import java.util.List;

public interface IProductHandler {
    List<ProductVM> getAllProducts();

    ProductVM getProductById(Long id);

    ProductVM createProduct(CreateProductDTO createProductDTO);

    ProductVM updateProduct(Long id, UpdateProductDTO updateProductDTO);

    void deleteProduct(Long id);

    List<ProductVM> getAllActiveProducts();
}
