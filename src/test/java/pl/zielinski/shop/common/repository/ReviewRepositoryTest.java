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
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository under;

    @Test
    void it_Should_Find_One_Review_By_Product_Id_And_Moderated() {
        //given
        Review review1 = getReview1();
        Review review2 = getReview2();
        Review review3 = getReview3();
        //when
        under.save(review1);
        under.save(review2);
        under.save(review3);
        //then
        List<Review> listOfReview = under.findAllByProductIdAndModerated(1L, true);
        assertThat(listOfReview).hasSize(1);
    }

    @Test
    void it_Should_Find_Zero_Reviews_By_Product_Id_And_Moderated() {
        //given
        Review review1 = getReview1();
        Review review2 = getReview2();
        //when
        under.save(review1);
        under.save(review2);
        //then
        List<Review> listOfReview = under.findAllByProductIdAndModerated(3L, true);
        assertThat(listOfReview).hasSize(0);
    }

    @Test
    void it_Should_Find_Zero_Reviews_By_Product_Id_And_Moderated_False() {
        //given
        Review review1 = getReview1();
        Review review2 = getReview2();
        //when
        under.save(review1);
        under.save(review2);
        //then
        List<Review> listOfReview = under.findAllByProductIdAndModerated(2L, false);
        assertThat(listOfReview).hasSize(1);
    }

    private Review getReview1() {
        return Review.builder()
                .id(1L)
                .productId(1L)
                .authorName("Rafał")
                .content("Good opinion")
                .moderated(true)
                .build();
    }

    private Review getReview2() {
        return Review.builder()
                .id(2L)
                .productId(2L)
                .authorName("Kamil")
                .content("Bad opinion")
                .moderated(false)
                .build();
    }

    private Review getReview3() {
        return Review.builder()
                .id(3L)
                .productId(3L)
                .authorName("Justyna")
                .content("Medium opinion")
                .moderated(true)
                .build();
    }
}
