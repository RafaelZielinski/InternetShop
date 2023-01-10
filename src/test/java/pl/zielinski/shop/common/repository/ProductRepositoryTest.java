package pl.zielinski.shop.common.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.category.repository.CategoryRepository;
import pl.zielinski.shop.common.model.Category;
import pl.zielinski.shop.common.model.Product;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest implements Products, Categories {
    @Autowired
    private ProductRepository under;
    @Autowired
    private CategoryRepository categoryRepository;




    @Transactional
    @Test
    void itShouldFindZeroBySlug() {
        //given

        categoryRepository.saveAll(List.of(category1(), category2(), category3(), category4(), category5()));
        System.out.println(categoryRepository.count());
        System.out.println(category1().toString());
        System.out.println(category2().toString());
        System.out.println(category3().toString());
        System.out.println(category4().toString());
        System.out.println(category5().toString());
        System.out.println(categoryRepository.count());
        under.saveAll(List.of(product1(), product2(), product3(), product4(), product5()));

        //When
        Optional<Product> listOfProducts = under.findBySlug("slug");

        System.out.println();
        System.out.println(product1().toString());
        System.out.println(product2().toString());
        System.out.println(product3().toString());
        System.out.println(product4().toString());
        System.out.println(product5().toString());
        //Then
        assertThat(listOfProducts).isEmpty();
    }

    @Test
    void itShouldFindOneProductBySlug() {
        //given
        categoryRepository.saveAll(List.of(category1(), category2(), category3(), category4(), category5()));
        under.saveAll(List.of(product1(), product2(), product3(), product4(), product5()));
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
        categoryRepository.saveAll(List.of(category1(), category2(), category3(), category4(), category5()));
        under.saveAll(List.of(product1(), product2(), product3(), product4(), product5()));
        //When
        Page<Product> list = under.findByCategoryId(2L, Pageable.ofSize(5));
        //Then
        assertThat(list).isEmpty();
    }

    @Test
    void itShouldFindFiveByCategoryId() {
        //given
        categoryRepository.saveAll(List.of(category1(), category2(), category3(), category4(), category5()));
        under.saveAll(List.of(product1(), product2(), product3(), product4(), product5()));
        Product product5 = product5();
        //When
        Page<Product> list = under.findByCategoryId(1L, Pageable.ofSize(5));
        //Then
        assertThat(list.getTotalElements()).isEqualTo(5L);
    }

}
