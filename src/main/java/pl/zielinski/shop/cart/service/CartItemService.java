package pl.zielinski.shop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.cart.repository.CartItemRepository;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

    public Long countItemInCart(Long cartId) {

        return cartItemRepository.countByCartId(cartId);
    }
}
