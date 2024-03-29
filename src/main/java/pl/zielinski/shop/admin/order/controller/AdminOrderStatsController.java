package pl.zielinski.shop.admin.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zielinski.shop.admin.order.model.dto.AdminOrderStats;
import pl.zielinski.shop.admin.order.service.AdminOrderStatsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders/stats")
public class AdminOrderStatsController {

    private final AdminOrderStatsService adminOrderStatsService;
    @GetMapping
    public AdminOrderStats getOrderStatistics() {
        return adminOrderStatsService.getStatistics();
    }
}
