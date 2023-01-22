package pl.zielinski.shop.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.common.model.Review;
import pl.zielinski.shop.common.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser
@ActiveProfiles(profiles = "test")
class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddOneReview() throws Exception {
        //given
        JSONObject jo = new JSONObject();
        jo.put("authorName", "Ariel");
        jo.put("content", "Cool product");
        jo.put("productId", "1");
        //when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Review expectedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Optional<Review> actualReview = reviewRepository.findById(expectedReview.getId());
        //then
        assertThat(actualReview).isNotNull();
    }

    @Test
    void shouldNotAddOneReview() throws Exception {
        //given
        //when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/review")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();

        //then
        List<Review> all = reviewRepository.findAll();
        assertThat(all).isNotNull();
    }

}
