package pl.zielinski.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.order.model.Payment;
import pl.zielinski.shop.order.repository.PaymentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;


    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }
}
