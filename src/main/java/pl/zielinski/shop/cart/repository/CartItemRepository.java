package pl.zielinski.shop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.cart.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Long countByCartId(Long cartId);
}
