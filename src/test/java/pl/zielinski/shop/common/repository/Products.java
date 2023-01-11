package pl.zielinski.shop.common.repository;

import pl.zielinski.shop.common.model.Product;

import java.math.BigDecimal;

public interface Products {

    default Product product1() {
        return Product.builder()
                .id(1L)
                .name("Szampon")
                .categoryId(1L)
                .description("Fryzjerstwo")
                .fullDescription("Haistyle advanced")
                .price(BigDecimal.valueOf(20.00))
                .currency("USD")
                .image("shampoo")
                .slug("oil")
                .build();
    }

    default Product product2() {
       return Product.builder()
                .id(2L)
                .name("Cream")
                .categoryId(2L)
                .description("Fryzjerstwo2")
                .fullDescription("Haistyle advanced2")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("cream")
                .slug("cream")
                .build();
    }
    default Product product3() {
        return Product.builder()
                .id(3L)
                .name("Comb")
                .categoryId(3L)
                .description("Fryzjerstwo2")
                .fullDescription("Haistyle advanced2")
                .price(BigDecimal.valueOf(20.00))
                .currency("EUR")
                .image("comb")
                .slug("skin")
                .build();
    }
    default Product product4() {
        return Product.builder()
                .id(4L)
                .name("Brush")
                .categoryId(4L)
                .description("Hairstyle-brush")
                .fullDescription("Haistyle advanced")
                .price(BigDecimal.valueOf(20.00))
                .currency("EUR")
                .image("brushes")
                .slug("Brush")
                .build();
    }
    default Product product5() {
        return Product.builder()
                .id(5L)
                .name("hair")
                .categoryId(1L)
                .description("Hair-style_brush")
                .fullDescription("Haistyle advanced2")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("hair2")
                .slug("man")
                .build();
    }
}
