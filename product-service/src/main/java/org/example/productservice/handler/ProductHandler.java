package org.example.productservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.entity.Category;
import org.example.productservice.entity.Product;
import org.example.productservice.mapper.ProductMapper;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.IProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductHandler implements IProductHandler {

    private final IProductService productService;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public List<ProductVM> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return productMapper.toVMList(products);
    }

    public List<ProductVM> getAllActiveProducts() {
        List<Product> products = productService.getAllActiveProducts();
        return productMapper.toVMList(products);
    }

    public ProductVM getProductById(Long id) {
        Product product = productService.getProductById(id).join();
        return productMapper.toProductVM(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductVM createProduct(CreateProductDTO createProductDTO) {
        Product product = productMapper.toProduct(createProductDTO);
        product.setCreatedAt(LocalDateTime.now());
        product.setStatus(true);

        Category category = categoryRepository.findById(createProductDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + createProductDTO.getCategoryId()));
        product.setCategory(category);

        Product savedProduct = productService.createProduct(createProductDTO);
        return productMapper.toProductVM(savedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductVM updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        Product product = productService.getProductById(id).join();

        productMapper.updateProductFromDTO(updateProductDTO, product);
        product.setUpdatedAt(LocalDateTime.now());

        Category category = categoryRepository.findById(updateProductDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + updateProductDTO.getCategoryId()));
        product.setCategory(category);

        Product updatedProduct = productService.updateProduct(id, updateProductDTO);
        return productMapper.toProductVM(updatedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }
}

