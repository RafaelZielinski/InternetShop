package pl.zielinski.shop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zielinski.shop.product.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);
}
