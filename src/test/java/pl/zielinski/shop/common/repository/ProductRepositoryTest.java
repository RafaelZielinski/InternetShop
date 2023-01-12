package pl.zielinski.shop.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.zielinski.shop.common.model.Product;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql({"/scripts_sql/initial_data_categories.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest implements Products{

    @Autowired
    private ProductRepository under;

    @BeforeEach
    void setUp() {
        under.saveAll(List.of(product1(), product2(), product3(), product4(), product5()));
    }

    @Test
    void itShouldFindZeroBySlug() {
        //given
        //When
        Optional<Product> listOfProducts = under.findBySlug("slug");
        //Then
        assertThat(listOfProducts).isEmpty();
    }

    @Test
    void itShouldFindOneProductBySlug() {
        //given
        //When
        Optional<Product> listOfProducts = under.findBySlug("oil");
        //Then
        assertThat(listOfProducts).hasValueSatisfying(c -> {
            assertThat(c.getSlug()).isEqualTo("oil");
        });
    }
    @Test
    void itShouldFindZeroProductsByCategoryId() {
        //given
        //When
        Page<Product> list = under.findByCategoryId(5L, Pageable.ofSize(5));
        //Then
        assertThat(list).isEmpty();
    }

    @Test
    void itShouldFindTwoProductsByCategoryId() {
        //given
        Product product5 = product5();
        //When
        Page<Product> list = under.findByCategoryId(1L, Pageable.ofSize(5));
        //Then
        assertThat(list.getTotalElements()).isEqualTo(2L);
    }

}
