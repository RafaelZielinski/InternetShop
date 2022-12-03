package pl.zielinski.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.zielinski.shop.common.model.Category;
import pl.zielinski.shop.common.dto.ProductListDto;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
