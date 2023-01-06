package pl.zielinski.shop.admin.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zielinski.shop.admin.order.model.AdminOrder;
import pl.zielinski.shop.common.dto.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminOrderRepository extends JpaRepository<AdminOrder, Long> {
    List<AdminOrder> findAllByPlaceDateIsBetweenAndOrderStatus(
            LocalDateTime from,
            LocalDateTime to,
            OrderStatus orderStatus);
}
