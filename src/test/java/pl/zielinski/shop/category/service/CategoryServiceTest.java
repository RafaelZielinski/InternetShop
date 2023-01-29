package pl.zielinski.shop.category.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.zielinski.shop.category.dto.CategoryProductsDto;
import pl.zielinski.shop.category.repository.CategoryRepository;
import pl.zielinski.shop.common.model.Category;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest implements ListOfCategories{

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;

    @Autowired
    @InjectMocks
    CategoryService categoryService;

    @Test
    void it_should_get_one_categories() {
        //Given
        List<Category> expected = one();
        when(categoryRepository.findAll()).thenReturn(one());
        //When
        List<Category> actual = categoryService.getCategories();
        //Then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getId()).isEqualTo(expected.get(0).getId());
        assertThat(actual.get(0).getName()).isEqualTo(expected.get(0).getName());
        assertThat(actual.get(0).getDescription()).isEqualTo(expected.get(0).getDescription());
        assertThat(actual.get(0).getSlug()).isEqualTo(expected.get(0).getSlug());
    }

    @Test
    void itShouldGetCategoriesWithProducts() {
        //Given
        List<Category> expected = two();
        when(categoryRepository.findAll()).thenReturn(two());
        //When
        List<Category> actual = categoryService.getCategories();
        //Then
        assertThat(actual).hasSize(2);
    }

    @Test
    void it_should_return_zero_categories() {
        //given
        List<Category> actual = Collections.EMPTY_LIST;
        when(categoryRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        //when
        List<Category> expected = categoryService.getCategories();
        //then
        assertThat(actual).hasSize(0);
    }

    @Test
    void it_should_return_zero_products() {
        //given
        when(categoryRepository.findBySlug(any())).thenReturn(category());
        when(productRepository.findByCategoryId(1L, Pageable.ofSize(10))).thenReturn(Page.empty());
        //when
        final CategoryProductsDto actual = categoryService.getCategoriesWithProducts(anyString(), Pageable.ofSize(10));
        //then
        assertThat(actual.products()).isEmpty();
    }

    @Test
    void it_should_return_one_products() {
        //given
        when(categoryRepository.findBySlug(any())).thenReturn(category());
        when(productRepository.findByCategoryId(1L, Pageable.ofSize(10))).thenReturn(new PageImpl<Product>(categoryListOne(), Pageable.ofSize(10), 1));
        //when
        final CategoryProductsDto actual = categoryService.getCategoriesWithProducts(anyString(), Pageable.ofSize(10));
        //then
        assertThat(actual.products()).hasSize(1);
    }
}
