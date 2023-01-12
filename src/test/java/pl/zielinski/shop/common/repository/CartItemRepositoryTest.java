package pl.zielinski.shop.common.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.zielinski.shop.common.model.CartItem;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql({  "/scripts_sql/initial_data_categories.sql",
        "/scripts_sql/initial_data_products.sql",
        "/scripts_sql/initial_data_carts.sql",
        "/scripts_sql/initial_data_cartitems.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository under;

    @Test
    void itShouldCountByCartIdAndGiveNumberTwoOfResults() {
        //Given
        //When
        final Long countByCartId = under.countByCartId(2L);
        //Then
        assertThat(countByCartId).isEqualTo(2);
    }

    @Test
    void itShouldCountByCartIdAndGiveNumberZeroOfResults() {
        //Given
        //When
        final Long countByCartId = under.countByCartId(6L);
        //Then
        assertThat(countByCartId).isEqualTo(0);
    }

    @Test
    void itShouldDeleteByCartIdAndGiveResultFour() {
        //Given
        //When
        under.deleteByCartId(2L);
        //Then
        assertThat(under.findAll()).hasSize(3);
    }

    @Test
    void itShouldDeleteByCartIdAndGiveResultZero() {
        //Given
        //When
        under.deleteByCartId(2L);
        under.deleteByCartId(1L);
        under.deleteByCartId(3L);
        under.deleteByCartId(4L);
        under.deleteByCartId(5L);
        //Then
        assertThat(under.findAll()).isEmpty();
    }

    @Test
    void itShouldDeleteAllByCartIdInAndLeaveOneElements() {
        //Given
        //When
        under.deleteAllByCartIdIn(List.of(1L, 2L, 3L));
        //Then
        assertThat(under.findAll()).hasSize(1);
    }

    @Test
    void itShouldDeleteAllByCartIdInAndLeaveNone() {
        //Given
        //When
        under.deleteAllByCartIdIn(List.of(1L, 2L, 3L, 4L, 5L));
        //Then
        assertThat(under.findAll()).isEmpty();
    }
}
