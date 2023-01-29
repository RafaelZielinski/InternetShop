package pl.zielinski.shop.category.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.category.dto.CategoryProductsDto;
import pl.zielinski.shop.category.repository.CategoryRepository;
import pl.zielinski.shop.category.service.ListOfCategories;
import pl.zielinski.shop.common.model.Category;
import pl.zielinski.shop.common.model.Product;
import pl.zielinski.shop.common.repository.ProductRepository;
import pl.zielinski.shop.product.controller.ProductController;

import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
@Sql({"/scripts_sql/initial_data_categories.sql",
        "/scripts_sql/initial_data_products.sql"})
class CategoryControllerIntegrationTest implements ListOfCategories {

    @Autowired
    private CategoryController categoryController;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Sql({"/scripts_sql/initial_data_categories.sql",
            "/scripts_sql/initial_data_products.sql"})

    @Test
    void it_should_get_one_product_by_category_id() throws Exception {
        //Given
        String slug = "inne1";
        //When
        //Then
        final MvcResult mvcResult = mockMvc.perform(get("/categories/{slug}/products", slug))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.products.content.length()").value(1))
                .andExpect(jsonPath("$.category.name").value("Jeden"))
                .andReturn();
    }

    @Test
    void it_should_get_categories() throws Exception {
        //Given
        //When
        final MvcResult mvcResult = mockMvc.perform(get("/categories"))
                .andExpect(status().is(200))
                .andReturn();
        List<Category> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        //Then
        assertThat(actual).hasSize(5);
    }

    @Test
    void it_should_get_zero_categories() throws Exception {
        //Given
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        //When
        final MvcResult mvcResult = mockMvc.perform(get("/categories"))
                .andExpect(status().is(200))
                .andReturn();
        List<Category> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        //Then
        assertThat(actual).hasSize(0);
    }
}
