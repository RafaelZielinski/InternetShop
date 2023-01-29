package pl.zielinski.shop.category.service;

import liquibase.pro.packaged.C;
import pl.zielinski.shop.category.dto.CategoryProductsDto;
import pl.zielinski.shop.common.model.Category;
import pl.zielinski.shop.common.model.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public interface ListOfCategories {
    default List<Category> zero() {
        return Collections.emptyList();
    }

    default List<Category> one() {
        return List.of(Category.builder()
                .id(1L)
                .name("Shoes")
                .description("obuwie")
                .slug("oneshoe")
                .build());
    }

    default List<Category> two() {
        return List.of(Category.builder()
                        .id(1L)
                        .name("Shoes")
                        .description("obuwie")
                        .slug("oneshoe")
                        .build(),
                Category.builder()
                        .id(2L)
                        .name("Statek ekwador")
                        .description("statki")
                        .slug("statek1")
                        .build()
        );
    }

    default Category category () {
        return Category.builder()
                .id(1L)
                .name("Shoes")
                .description("obuwie")
                .slug("oneshoe")
                .build();
    }

    default List<Product> categoryListOne() {
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("Spoon")
                        .categoryId(1L)
                        .description("cutlery")
                        .fullDescription("kitchen")
                        .price(BigDecimal.valueOf(10.00))
                        .currency("PLN")
                        .image("house")
                        .slug("fork")
                        .build()
        );
    }


//    default CategoryProductsDto category() {
//        final Category category = Category.builder()
//                .id(2L)
//                .name("Statek ekwador")
//                .description("statki")
//                .slug("statek1")
//                .build();
//        return new CategoryProductsDto(
//                category.getId(),
//                category.getName(),
//                category.getDescription(),
//                category.getSlug()
//        )
//    }
}
