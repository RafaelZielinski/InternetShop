package pl.zielinski.shop.admin.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.admin.order.model.AdminOrderLog;

@Repository
public interface AdminOrderLogRepository extends JpaRepository<AdminOrderLog, Long> {


}
