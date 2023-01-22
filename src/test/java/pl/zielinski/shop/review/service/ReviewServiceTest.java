package pl.zielinski.shop.review.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pl.zielinski.shop.common.model.Review;
import pl.zielinski.shop.common.repository.ReviewRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Captor
    private ArgumentCaptor<Review> customerArgumentCaptor;

    @Autowired
    @InjectMocks
    private ReviewService under;

    @Test
    void should_add_one_review() {
        //given
        Review review1 = Review.builder()
                .id(1L)
                .productId(2L)
                .authorName("Kamil")
                .content("love story")
                .moderated(false)
                .build();
        //when
        under.addReview(review1);
        //then
        then(reviewRepository).should().save(customerArgumentCaptor.capture());
        Review reviewArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(reviewArgumentCaptorValue).isEqualTo(review1);
    }

    @Test
    void should_add_no_reviews() {
        //given
        Review review1 = Review.builder()
                .id(1L)
                .productId(2L)
                .authorName("Kamil")
                .content("love story")
                .moderated(false)
                .build();
        //when
        Review actual = under.addReview(review1);
        //then
        assertThat(actual).isNull();

    }



}
