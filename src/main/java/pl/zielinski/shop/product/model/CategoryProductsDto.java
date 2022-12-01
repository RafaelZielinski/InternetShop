package pl.zielinski.shop.product.model;

import org.springframework.data.domain.Page;
import pl.zielinski.shop.category.model.Category;

public record CategoryProductsDto(Category category, Page<Product> products) {
}
