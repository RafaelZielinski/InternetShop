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

    @Mock
    private CartItemRepository cartItemRepository;


    @Autowired
    @InjectMocks
    private CartRepository under;


    Clock clock;
    String instantExpected;


    @BeforeEach
    void setUp() {
       clock = Clock.fixed(Instant.parse("2023-01-10T10:15:30.00Z"), ZoneId.of("UTC"));
       instantExpected = "2023-01-10T10:15:30Z";
    }

    @Test
    void findByCreatedLessThan() {
        Cart cart1 = new Cart(1L,
                LocalDateTime.now(clock),
                List.of(new CartItem(1L, 20,
                        new Product(1L, "Ser", 1L,"Diary", "Good Diary", BigDecimal.valueOf(20.00), "PLN", "cheese", "diary" ),
                            1L)));
        //given
        under.save(cart1);
        List<Cart> list = under.findByCreatedLessThan(LocalDateTime.now(clock).minusNanos(1));
        //when
        assertThat(list).hasSize(1);
        //then
    }
}
