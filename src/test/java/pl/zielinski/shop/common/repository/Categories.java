package pl.zielinski.shop.common.repository;

import pl.zielinski.shop.common.model.Category;

public interface Categories {
    default Category category1() {
        return Category.builder()
                .id(1L)
                .name("Building")
                .description("build stuff")
                .slug("builder")
                .build();
    }

    default Category category2() {
        return Category.builder()
                .id(2L)
                .name("Painting")
                .description("paint")
                .slug("painter")
                .build();
    }

    default Category category3() {
        return Category.builder()
                .id(3L)
                .name("cooking")
                .description("cook")
                .slug("cooker")
                .build();
    }

    default Category category4() {
        return Category.builder()
                .id(4L)
                .name("swiming")
                .description("swim")
                .slug("swimer")
                .build();
    }

    default Category category5() {
        return Category.builder()
                .id(5L)
                .name("watching")
                .description("watch")
                .slug("watcher")
                .build();
    }
}
