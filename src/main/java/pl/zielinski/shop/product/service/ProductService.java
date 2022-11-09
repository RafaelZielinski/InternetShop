package pl.zielinski.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.product.model.Product;
import pl.zielinski.shop.product.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getProducts(@PageableDefault(size = 10) Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
