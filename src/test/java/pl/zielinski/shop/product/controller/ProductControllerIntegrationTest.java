package pl.zielinski.shop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.common.dto.ProductListDto;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.ProductRepository;
import pl.zielinski.shop.exception.NoSuchElementFoundException;
import pl.zielinski.shop.product.service.dto.ProductDto;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
@Sql({"/scripts_sql/initial_data_categories.sql",
        "/scripts_sql/initial_data_products.sql"})
class ProductControllerIntegrationTest {

    @Autowired
    private ProductController productController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_product_by_slug() throws Exception {
    //given
    String slug = "slug1";
    //when
        MvcResult mvcResult = mockMvc.perform(get("/products/{slug}", slug))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    //then
    ProductDto expected = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDto.class);
    Product actual = productRepository.findBySlug(slug).orElseThrow();
    assertThat(actual.getSlug()).isEqualTo(expected.getSlug());
    assertThat(actual.getCurrency()).isEqualTo(expected.getCurrency());
    assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    @Test
    void should_return_none_by_slug() throws Exception {
        //given
        String slug = "slug1";
        //when
        MvcResult mvcResult = mockMvc.perform(
                        get("/products/{slug}", slug))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        ProductDto expected = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDto.class);
        Product actual = productRepository.findBySlug("error").orElse(null);
        assertThat(actual).isNull();
        assertThat(expected).isNotNull();
    }

    @Test
    void should_throw_exception_when_enter_wrong_slug() throws Exception {
        //given
        String slug = "wrong";
        //when
        mockMvc.perform(get("/products/{slug}", slug)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThatExceptionOfType(NoSuchElementFoundException.class))
                .andExpect(result -> assertEquals("No value present", Objects.requireNonNull(result.getResolvedException()).getMessage()));
        //then
    }

    @Test
    void should_return_five_products() throws Exception {
        //given
        ResultActions result = mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON));
        //when
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(5))
                .andReturn();
        //then


    }

    @Test
    void should_return_zero_products() throws Exception {
        productRepository.deleteAll();
        //given
        ResultActions result = mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON));
        //when
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(0))
                .andReturn();
        //then


    }

    private List<ProductListDto> getProductListDtos(Page<Product> products) {
        return products.getContent().stream()
                .map(product -> ProductListDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .currency(product.getCurrency())
                        .image(product.getImage())
                        .slug(product.getSlug())
                        .build())
                .toList();
    }

}
