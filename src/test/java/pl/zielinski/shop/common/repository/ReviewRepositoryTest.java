package pl.zielinski.shop.common.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.zielinski.shop.common.model.Review;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles(value = "test")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest implements Reviews{

    @Autowired
    private ReviewRepository under;

    @Test
    void itShouldFindOneReviewByProductIdAndModerated() {
        //given
        Review review1 = review1();
        Review review2 = review2();
        Review review3 = review3();
        //when
        under.saveAll(List.of(review1, review2, review3));
        //then
        List<Review> listOfReview = under.findAllByProductIdAndModerated(1L, true);
        assertThat(listOfReview).hasSize(1);
    }

    @Test
    void itShouldFindZeroReviewsByProductIdAndModerated_False() {
        //given
        Review review1 = review1();
        Review review2 = review2();
        Review review3 = review3();
        //when
        under.saveAll(List.of(review1, review2, review3));
        //then
        List<Review> listOfReview = under.findAllByProductIdAndModerated(3L, false);
        assertThat(listOfReview).isEmpty();
    }

    @Test
    void itShouldFindTwoReviewsByProductIdAndModeratedTrue() {
        //given
        Review review1 = review1();
        Review review2 = review2();
        //when
        under.saveAll(List.of(review1, review2));
        //then
        List<Review> listOfReview = under.findAllByProductIdAndModerated(2L, true);
        assertThat(listOfReview).isEmpty();
    }
}
