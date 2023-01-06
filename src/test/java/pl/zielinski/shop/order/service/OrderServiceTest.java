package pl.zielinski.shop.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zielinski.shop.common.mail.EmailClientService;
import pl.zielinski.shop.common.mail.FakeEmailService;
import pl.zielinski.shop.common.model.Cart;
import pl.zielinski.shop.common.model.CartItem;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.CartItemRepository;
import pl.zielinski.shop.common.repository.CartRepository;
import pl.zielinski.shop.order.model.OrderStatus;
import pl.zielinski.shop.order.model.Payment;
import pl.zielinski.shop.order.model.PaymentType;
import pl.zielinski.shop.order.model.Shipment;
import pl.zielinski.shop.order.model.dto.OrderDto;
import pl.zielinski.shop.order.model.dto.OrderSummary;
import pl.zielinski.shop.order.repository.OrderRepository;
import pl.zielinski.shop.order.repository.OrderRowRepository;
import pl.zielinski.shop.order.repository.PaymentRepository;
import pl.zielinski.shop.order.repository.ShipmentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private final static LocalDateTime PLACE_DATE = LocalDateTime.now();
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ShipmentRepository shipmentRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRowRepository orderRowRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private EmailClientService emailClientService;
    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldPlaceOrder() {
        //given
        OrderDto orderDto = createOrderDto();
        when(cartRepository.findById(any())).thenReturn(createCart());
        when(shipmentRepository.findById(any())).thenReturn(createShipment());
        when(paymentRepository.findById(any())).thenReturn(createPayment());
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(emailClientService.getInstance()).thenReturn(new FakeEmailService());
        Long userId = 1L;
        //when
        OrderSummary orderSummary = orderService.placeOrder(orderDto, userId);
        //then
        assertThat(orderSummary).isNotNull();
        assertThat(orderSummary.getStatus()).isEqualTo(OrderStatus.NEW);

        assertThat(orderSummary.getGrossValue()).isEqualTo(new BigDecimal("36.22"));
        assertThat(orderSummary.getPayment().getType()).isEqualTo(PaymentType.BANK_TRANSFER);
        assertThat(orderSummary.getPayment().getName()).isEqualTo("test payment");
        assertThat(orderSummary.getPayment().getId()).isEqualTo(5L);
    }

    private Optional<Payment> createPayment() {
        return Optional.of(Payment.builder()
                .id(5L)
                .name("test payment")
                .type(PaymentType.BANK_TRANSFER)
                .defaultPayment(true)
                .note("Szybka dostawa")
                .build());
    }

    private Optional<Shipment> createShipment() {
        return Optional.of(
                Shipment.builder()
                        .id(2L)
                        .price(new BigDecimal("14.00"))
                        .build()
        );
    }

    private Optional<Cart> createCart() {
        return Optional.of(Cart.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .items(createItems())
                .build());
    }

    private List<CartItem> createItems() {
            return List.of(
                    CartItem.builder()
                            .id(1L)
                            .cartId(1L)
                            .quantity(1)
                            .product(Product.builder()
                                    .id(1L)
                                    .price(new BigDecimal("11.11"))
                                    .build())
                            .build(),
                    CartItem.builder()
                            .id(2L)
                            .cartId(1L)
                            .quantity(1)
                            .product(Product.builder()
                                    .id(2L)
                                    .price(new BigDecimal("11.11"))
                                    .build())
                            .build()
            );

    }

    private OrderDto createOrderDto() {
        return OrderDto.builder()
                .firstname("Marek")
                        .lastname("Kibała")
                                .street("Sanok 15")
                                        .zipcode("38-600")
                                                .city("Sanok")
                                                        .email("marek.kibala@wp.pl")
                                                                .phone("722145931")
                                                                        .cartId(1L)
                                                                                .shipmentId(2L)
                                                                                        .paymentId(3L)
                                                                                                .build();
    }

}
