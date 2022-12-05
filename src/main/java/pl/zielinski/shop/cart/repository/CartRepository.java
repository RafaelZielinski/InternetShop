package pl.zielinski.shop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.cart.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
