package org.example.productservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.entity.Category;
import org.example.productservice.entity.Product;
import org.example.productservice.mapper.ProductMapper;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public List<ProductVM> getAllProducts() {
        return productMapper.toVMList(productRepository.findAll());
    }

    @Cacheable(value = "products", key = "#id")
    public ProductVM getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toProductVM(product);
    }

    public ProductVM createProduct(CreateProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        product.setCreatedAt(LocalDateTime.now());
        product.setStatus(true);
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId())));
        Product saved = productRepository.save(product);
        return productMapper.toProductVM(saved);
    }

    @CachePut(value = "products", key = "#id")
    public ProductVM updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productMapper.updateProductFromDTO(dto, product);
        product.setUpdatedAt(LocalDateTime.now());
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId())));
        Product updated = productRepository.save(product);
        return productMapper.toProductVM(updated);
    }

    @CacheEvict(value = "products", key = "#id")
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
