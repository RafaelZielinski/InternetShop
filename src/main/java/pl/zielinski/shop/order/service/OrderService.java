package pl.zielinski.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.common.mail.EmailClientService;
import pl.zielinski.shop.common.model.Cart;
import pl.zielinski.shop.common.repository.CartItemRepository;
import pl.zielinski.shop.common.repository.CartRepository;
import pl.zielinski.shop.order.model.Order;
import pl.zielinski.shop.order.model.Payment;
import pl.zielinski.shop.order.model.Shipment;
import pl.zielinski.shop.order.model.dto.OrderDto;
import pl.zielinski.shop.order.model.dto.OrderSummary;
import pl.zielinski.shop.order.repository.OrderRepository;
import pl.zielinski.shop.order.repository.OrderRowRepository;
import pl.zielinski.shop.order.repository.PaymentRepository;
import pl.zielinski.shop.order.repository.ShipmentRepository;
import static pl.zielinski.shop.order.service.mapper.OrderMapper.createNewOrder;
import static pl.zielinski.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.zielinski.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.zielinski.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;
import static pl.zielinski.shop.order.service.mapper.OrderMessageServiceMapper.createEmailMessage;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, Long userId) {

        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        Order order = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment, userId));
        saveOrderRows(cart, order.getId(), shipment);
        clearOrderCart(orderDto);
        sendConfirmEmail(order);
        return createOrderSummary(payment, order);
    }

    private void sendConfirmEmail(Order order) {
        emailClientService.getInstance()
                .send(order.getEmail(),
                        "Twoje zamówienie zostało przyjęte", createEmailMessage(order));
    }

    private void clearOrderCart(OrderDto orderDto) {

        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        saveProductRows(cart, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(orderId, shipment));
    }

    private void saveProductRows(Cart cart, Long orderId) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowWithQuantity(orderId, cartItem)
                )
                .peek(orderRowRepository::save)
                .toList();
    }
}
