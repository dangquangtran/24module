package org.example.productservice.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.productservice.common.exception.ResourceNotFoundException;
import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.UpdateProductDTO;
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
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return productRepository.findAll().stream()
                .filter(Product::getStatus)
                .toList();
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    @CircuitBreaker(name = "backendA", fallbackMethod = "fallbackMethod")
    @Retry(name = "backendA", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackMethod")
    @Bulkhead(name = "backendA", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "backendA", fallbackMethod = "fallbackMethod")
    public CompletableFuture<Product> getProductById(Long id) {
        return CompletableFuture.supplyAsync(() ->
                productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id))
        );
    }

    public CompletableFuture<Product> fallbackMethod(Long id, Throwable t) {
        Product fallbackProduct = new Product();
        fallbackProduct.setId(id);
        fallbackProduct.setName("Fallback Product");
        fallbackProduct.setDescription("Service unavailable: " + t.getMessage());
        return CompletableFuture.completedFuture(fallbackProduct);
    }

    @Override
    public Product createProduct(CreateProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        product.setCreatedAt(LocalDateTime.now());
        product.setStatus(true);
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId())));
        return productRepository.save(product);
    }

    @Override
    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productMapper.updateProductFromDTO(dto, product);
        product.setUpdatedAt(LocalDateTime.now());
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId())));
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
