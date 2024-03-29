package pl.zielinski.shop.cart.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zielinski.shop.common.model.Cart;
import pl.zielinski.shop.cart.model.dto.CartProductDto;
import pl.zielinski.shop.common.repository.CartRepository;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService under;

    @Test
    void shouldAddProductToCartWhenCartIdNotExists() {
        //given
        Long id = 0L;
        CartProductDto cartProductDto = new CartProductDto(1L, 1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).build()));
        when(cartRepository.save(any())).thenReturn(Cart.builder().id(1L).build());
        //when
        Cart result = under.addProductToCart(id, cartProductDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldAddProductToCartWhenCartIdExists() {
        //given
        Long id = 1L;
        CartProductDto cartProductDto = new CartProductDto(1L, 1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).build()));
        when(cartRepository.findById(id)).thenReturn(Optional.of(Cart.builder().id(1L).build()));
        //when
        Cart result = under.addProductToCart(id, cartProductDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
}
