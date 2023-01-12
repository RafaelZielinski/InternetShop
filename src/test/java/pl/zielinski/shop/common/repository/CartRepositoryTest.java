package pl.zielinski.shop.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.zielinski.shop.common.model.Cart;
import pl.zielinski.shop.common.model.CartItem;
import pl.zielinski.shop.common.model.Product;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(
        properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
@Sql({"/scripts_sql/initial_data_carts.sql"})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartRepositoryTest {

    @Autowired
    private CartRepository under;

    Clock clock;
    String instantExpected;


    @BeforeEach
    void setUp() {
       clock = Clock.fixed(Instant.parse("2023-01-10T10:15:30.00Z"), ZoneId.of("UTC"));
       instantExpected = "2023-01-10T10:15:30Z";
    }

    @Test
    void itShouldFindOneCartByCreatedBeforeDate() {
        Cart cart1 = Cart.builder()
                .id(1L)
                .created(LocalDateTime.now(clock))
                .build();
        //given
        under.save(cart1);
        List<Cart> list = under.findByCreatedLessThan(LocalDateTime.now(clock).plusSeconds(1));
        //when
        assertThat(list).hasSize(1);
        //then
    }

    @Test
    void itShouldFindTwoCartsByCreatedBeforeDate() {
        Cart cart1 = Cart.builder()
                .id(1L)
                .created(LocalDateTime.now(clock))
                .build();

        Cart cart2 = Cart.builder()
                .id(2L)
                .created(LocalDateTime.now(clock))
                .build();
        //given
        under.save(cart1);
        under.save(cart2);
        List<Cart> list = under.findByCreatedLessThan(LocalDateTime.now(clock).plusSeconds(1));
        //when
        assertThat(list).hasSize(2);
        //then
    }

    @Test
    void itShouldNotFindAnyCartsByCreatedBeforeDate() {
        Cart cart1 = Cart.builder()
                .id(6L)
                .created(LocalDateTime.now(clock).plusDays(1))
                .build();

        Cart cart2 = Cart.builder()
                .id(7L)
                .created(LocalDateTime.now(clock).plusDays(1))
                .build();
        //given
        under.save(cart1);
        under.save(cart2);
        List<Cart> list = under.findByCreatedLessThan(LocalDateTime.now(clock).plusSeconds(1));
        //when
        assertThat(list).isEmpty();
        //then
    }

    @Test
    void itShouldDeleteAllCarts() {
        //given
        Cart cart1 = under.findById(1L).orElseThrow();
        Cart cart2 = under.findById(2L).orElseThrow();
        Cart cart3 = under.findById(3L).orElseThrow();
        Cart cart4 = under.findById(4L).orElseThrow();
        Cart cart5 = under.findById(5L).orElseThrow();
        assertThat(under.findAll()).hasSize(5);
        //when
        under.deleteAllByIdIn(List.of(cart1.getId(), cart2.getId(), cart3.getId(), cart4.getId(), cart5.getId()));
        //then
        assertThat(under.findAll()).isEmpty();
    }

    @Test
    void itShouldNotDeleteAllCarts() {
        //given
        Cart cart1 = under.findById(1L).orElseThrow();

        assertThat(under.findAll()).hasSize(5);
        //when
        under.deleteAllByIdIn(List.of(cart1.getId()));
        //then
        assertThat(under.findAll()).hasSize(4);

    }


}
