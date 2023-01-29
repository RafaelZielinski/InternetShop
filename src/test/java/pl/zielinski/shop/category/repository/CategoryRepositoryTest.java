package pl.zielinski.shop.category.repository;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.zielinski.shop.common.model.Category;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/scripts_sql/initial_data_categories.sql"})
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void it_should_find_one_category_by_slug() {
        //Given
        String slug = "inne1";
        //When
        final Category actual = categoryRepository.findBySlug(slug);
        //Then
        final Optional<Category> expected = categoryRepository.findById(1L);
        assertThat(actual).isEqualTo(expected.get());
    }

    @Test
    void it_should_throw_exception_using_out_of_database_slug() {
        //Given
        String slug = "different";
        //When
        final Category actual = categoryRepository.findBySlug(slug);
        //Then
        assertThat(actual).isNull();
    }


}
