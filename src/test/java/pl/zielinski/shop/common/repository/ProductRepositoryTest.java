package pl.zielinski.shop.common.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import pl.zielinski.shop.common.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository under;

    @Test
    void it_Should_Find_One_By_Slug() {
        //Given
        Product product1 = Product.builder()
                .id(1L)
                .name("Szampon")
                .categoryId(1L)
                .description("Fryzjerstwo")
                .fullDescription("Haistyle advanced")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("szampon")
                .slug("hair")
                .build();
        //When
        under.save(product1);
        //Then
        Optional<Product> listOfProducts = under.findBySlug("hair");
        assertThat(listOfProducts)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getSlug()).isEqualTo("hair");
                });
    }

    @Test
    void it_Should_Find_Two_CategoryId_By_Slug() {
        Product product1 = Product.builder()
                .id(1L)
                .name("Szampon")
                .categoryId(1L)
                .description("Fryzjerstwo")
                .fullDescription("Haistyle advanced")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("szampon")
                .slug("bread")
                .build();
        Product product2 = Product.builder()
                .id(1L)
                .name("Szampon2")
                .categoryId(1L)
                .description("Fryzjerstwo2")
                .fullDescription("Haistyle advanced2")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("szampon2")
                .slug("skin")
                .build();
        //When
        under.save(product1);
        under.save(product2);
        //Then
        Optional<Product> listOfProducts = under.findBySlug("hair");
        assertThat(listOfProducts)
                .isEmpty();
    }

    @Test
    void it_Should_Find_One_CategoryId() {
        Product product1 = Product.builder()
                .id(3L)
                .name("Szampon")
                .categoryId(1L)
                .description("Fryzjerstwo")
                .fullDescription("Haistyle advanced")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("szampon")
                .slug("bread")
                .build();

        //When
        under.save(product1);
        //Then
        Page<Product> list= under.findByCategoryId(1L, Pageable.ofSize(5));
        assertThat(list.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void it_Should_Find_Zero_By_CategoryId() {
        Product product1 = Product.builder()
                .id(4L)
                .name("Szampon")
                .categoryId(1L)
                .description("Fryzjerstwo")
                .fullDescription("Haistyle advanced")
                .price(BigDecimal.valueOf(20.00))
                .currency("PLN")
                .image("szampon")
                .slug("bread")
                .build();

        //When
        under.save(product1);
        //Then
        Page<Product> list= under.findByCategoryId(2L, Pageable.ofSize(5));
        assertThat(list).isEmpty();
    }


}
