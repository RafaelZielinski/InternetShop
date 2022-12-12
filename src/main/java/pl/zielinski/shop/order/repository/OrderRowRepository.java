package pl.zielinski.shop.order.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.zielinski.shop.order.model.OrderRow;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
}
