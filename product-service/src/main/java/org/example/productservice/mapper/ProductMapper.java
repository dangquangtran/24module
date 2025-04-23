package org.example.productservice.mapper;

import org.example.productservice.dto.product.CreateProductDTO;
import org.example.productservice.dto.product.ProductVM;
import org.example.productservice.dto.product.UpdateProductDTO;
import org.example.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category", target = "category")
    ProductVM toProductVM(Product product);

    Product toProduct(CreateProductDTO dto);
    void updateProductFromDTO(UpdateProductDTO dto, @MappingTarget Product product);

    List<ProductVM> toVMList(List<Product> products);
}
