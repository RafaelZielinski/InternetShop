package pl.zielinski.shop.category.model;

import org.springframework.data.domain.Page;
import pl.zielinski.shop.product.controller.dto.ProductListDto;
import pl.zielinski.shop.product.model.Product;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
