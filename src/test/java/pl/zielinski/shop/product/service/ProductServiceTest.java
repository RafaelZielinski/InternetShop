package pl.zielinski.shop.product.service;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.model.Review;
import pl.zielinski.shop.common.repository.ProductRepository;
import pl.zielinski.shop.common.repository.ReviewRepository;
import pl.zielinski.shop.exception.NoSuchElementFoundException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void should_return_one_product() {
        //given
        final Product product = Product.builder()
                .id(1L)
                .name("Alan")
                .categoryId(1L)
                .description("small")
                .fullDescription("big")
                .price(BigDecimal.valueOf(1))
                .currency("PLN")
                .image("snake")
                .slug("python")
                .build();
        Pageable pageable = Pageable.ofSize(10);
        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<Product>(List.of(product)));
        //when
        Page<Product> products = productService.getProducts(Pageable.ofSize(10));
        //then
        assertThat(products.getTotalElements()).isEqualTo(1L);
        assertThat(products.stream().filter(element -> element.getId().equals(1L)).findFirst()).containsSame(product);
    }

    @Test
    void should_return_zero_product() {
        Pageable pageable = Pageable.ofSize(10);
        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<Product>(Collections.emptyList()));
        //when
        Page<Product> products = productService.getProducts(Pageable.ofSize(10));
        //then
        assertThat(products.getTotalElements()).isZero();
        assertThat(products.stream().toList()).isEmpty();
    }

    @Test
    void should_throw_error_when_slug_is_out_of_database() {
        //given
        String slug = "word";
        //when
        when(productRepository.findBySlug(slug)).thenThrow(new NoSuchElementFoundException("No value present"));
        //then
        assertThatThrownBy(() -> productService.getProductsBySlug(slug))
                .isInstanceOf(NoSuchElementFoundException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    void should_return_one_product_by_slug() {
        //given
        final Product product = Product.builder()
                .id(1L)
                .name("Alan")
                .categoryId(1L)
                .description("small")
                .fullDescription("big")
                .price(BigDecimal.valueOf(1))
                .currency("PLN")
                .image("snake")
                .slug("python")
                .build();
        String slug = "1L";
        //when
        when(productRepository.findBySlug(slug)).thenThrow(new NoSuchElementFoundException("No value present"));
        //then
        assertThatThrownBy(() -> productService.getProductsBySlug(slug))
                .isInstanceOf(NoSuchElementFoundException.class)
                .hasMessageContaining("No value present");
    }


}
