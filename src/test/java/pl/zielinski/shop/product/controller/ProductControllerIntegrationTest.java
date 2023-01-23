package pl.zielinski.shop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.ProductRepository;
import pl.zielinski.shop.product.service.dto.ProductDto;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    void should_Return_Product_By_Slug() throws Exception {
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
    Product actual = productRepository.findBySlug(slug).orElseThrow();
    assertThat(actual.getSlug()).isEqualTo(expected.getSlug());
    assertThat(actual.getCurrency()).isEqualTo(expected.getCurrency());
    assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    @Test
    void should_Return_None_By_Slug() throws Exception {
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
        /* I can not handle with throwing exception during this integration invoke get product by slug*/
    // That mockmvc doesn't catch this error but throw on screen
//    @Test
//    void should_Throw_Exception_When_Enter_Wrong_Slug() throws Exception {
//        //given
//        String slug = "wrong";
//        //when
//        mockMvc.perform(get("/products/{slug}", slug))
////                        .contentType(MediaType.APPLICATION_JSON));
////                .andExpect(status().isInternalServerError());
//                .andExpect(result -> assertThatExceptionOfType(NoSuchElementException.class));
////                .andExpect(result -> assertEquals("No value present", Objects.requireNonNull(result.getResolvedException()).getMessage()));
//        //then
//    }

}
