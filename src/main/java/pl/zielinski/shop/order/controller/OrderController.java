package pl.zielinski.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zielinski.shop.order.model.dto.InitOrder;
import pl.zielinski.shop.order.model.dto.OrderDto;
import pl.zielinski.shop.order.model.dto.OrderSummary;
import pl.zielinski.shop.order.service.OrderService;
import pl.zielinski.shop.order.service.ShipmentService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final ShipmentService shipmentService;
    private final OrderService orderService;

    @PostMapping()
    public OrderSummary placeOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.placeOrder(orderDto);
    }

    @GetMapping("/initData")
    public InitOrder initData() {
        return InitOrder.builder()
                .shipments(shipmentService.getShipments())
                .build();
    }

}
