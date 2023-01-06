package pl.zielinski.shop.order.service.mapper;

import pl.zielinski.shop.order.model.Order;
import pl.zielinski.shop.order.model.dto.OrderListDto;

import java.util.List;

public class OrderDtoMapper {

    public static List<OrderListDto> mapToOrderListDto(List<Order> byUserId) {
        return byUserId.stream()
                .map(order -> new OrderListDto(
                        order.getId(),
                        order.getPlaceDate(),
                        order.getOrderStatus().getValue(),
                        order.getGrossValue()))
                .toList();
    }
}
