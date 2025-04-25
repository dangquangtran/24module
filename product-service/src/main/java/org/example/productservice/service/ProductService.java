package org.example.productservice.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.productservice.common.exception.ResourceNotFoundException;
import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.entity.Product;
import org.example.productservice.mapper.ProductMapper;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.slf4j.MDC;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    public List<ProductVM> getAllProducts() {
        return productMapper.toVMList(productRepository.findAll());
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallbackMethod")
    @Retry(name = "backendA", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackMethod")
    @Bulkhead(name = "backendA", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "backendA", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ProductVM> getProductById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found"));
            return productMapper.toProductVM(product);
        });
    }

    public CompletableFuture<ProductVM> fallbackMethod(Long id, Throwable t) {
        ProductVM fallback = new ProductVM();
        fallback.setId(id);
        fallback.setName("Fallback async");
        fallback.setDescription("Timeout hoặc lỗi hệ thống: " + t.getMessage());
        return CompletableFuture.completedFuture(fallback);
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
