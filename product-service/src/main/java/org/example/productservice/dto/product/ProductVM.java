package org.example.productservice.dto.product;

import lombok.Data;
import org.example.productservice.entity.Category;

import java.time.LocalDateTime;

@Data
public class ProductVM {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;
    private Category category;
}