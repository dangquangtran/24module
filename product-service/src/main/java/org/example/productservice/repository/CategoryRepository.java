package org.example.productservice.repository;

import org.example.productservice.entity.Category;
import org.example.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
