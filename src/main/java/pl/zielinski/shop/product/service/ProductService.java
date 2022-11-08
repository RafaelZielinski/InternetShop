package pl.zielinski.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.product.model.Product;
import pl.zielinski.shop.product.repository.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
