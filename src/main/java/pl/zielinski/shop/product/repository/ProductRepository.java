package pl.zielinski.shop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zielinski.shop.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
