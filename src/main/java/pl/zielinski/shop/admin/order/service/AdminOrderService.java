package pl.zielinski.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.zielinski.shop.admin.order.model.AdminOrder;
import pl.zielinski.shop.admin.order.model.AdminOrderStatus;
import pl.zielinski.shop.admin.order.repository.AdminOrderRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final AdminOrderRepository orderRepository;

    public Page<AdminOrder> getOrders(Pageable pageable) {
        return orderRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("id").descending())
        );
    }

    public AdminOrder getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void patchOrder(Long id, Map<String, String> values) {
        final AdminOrder adminOrder = orderRepository.findById(id).orElseThrow();
        patchValues(adminOrder, values);
    }


    private void patchValues(AdminOrder adminOrder, Map<String, String> values) {
        if(values.get("orderStatus") != null) {
            adminOrder.setOrderStatus(AdminOrderStatus.valueOf(values.get("orderStatus")));
        }
    }
}
