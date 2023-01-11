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
import pl.zielinski.shop.common.model.Cart;
import pl.zielinski.shop.common.model.CartItem;
import pl.zielinski.shop.common.model.Product;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
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
                .id(1L)
                .created(LocalDateTime.now(clock).plusDays(1))
                .build();

        Cart cart2 = Cart.builder()
                .id(2L)
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
}
