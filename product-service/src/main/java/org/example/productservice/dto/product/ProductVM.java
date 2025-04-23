package org.example.productservice.dto.product;

import lombok.Data;
import org.example.productservice.dto.category.CategoryVM;
import org.example.productservice.entity.Category;

import java.time.LocalDateTime;

@Data
public class ProductVM {
    private Long id;
    private String name;
    private String description;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;
    private Category category;
}