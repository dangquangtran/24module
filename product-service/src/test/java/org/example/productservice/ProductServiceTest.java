package org.example.productservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.productservice.entity.Category;
import org.example.productservice.entity.Product;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Test Category");

        Product mockProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(true)
                .category(mockCategory)
                .build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        CompletableFuture<Product> productFuture = productService.getProductById(1L);

        Product result = productFuture.join();
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    public void testGetAllProducts() {
        // Mock dữ liệu trả về từ productRepository
        Product product1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now())
                .status(true)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .createdAt(LocalDateTime.now())
                .status(true)
                .build();

        List<Product> productList = Arrays.asList(product1, product2);

        // Khi gọi productRepository.findAll(), trả về danh sách mock
        when(productRepository.findAll()).thenReturn(productList);

        // Gọi service
        List<Product> result = productService.getAllProducts();

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());

        // Đảm bảo repository được gọi đúng 1 lần
        verify(productRepository, times(1)).findAll();
    }
}
