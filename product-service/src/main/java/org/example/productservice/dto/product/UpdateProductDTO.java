package org.example.productservice.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductDTO {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 2, max = 50, message = "Description must be between 2 and 50 characters")
    private String description;

    @PositiveOrZero(message = "Price must be a positive number or zero")
    private Double price;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private Boolean status;
}
