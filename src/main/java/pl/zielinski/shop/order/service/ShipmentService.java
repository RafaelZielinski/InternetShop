package pl.zielinski.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.order.model.Shipment;
import pl.zielinski.shop.order.repository.ShipmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

}
