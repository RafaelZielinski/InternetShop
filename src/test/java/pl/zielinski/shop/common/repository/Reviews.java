package pl.zielinski.shop.common.repository;

import pl.zielinski.shop.common.model.Review;

public interface Reviews {

    default Review review1() {
        return Review.builder()
                .id(1L)
                .productId(1L)
                .authorName("Rafael")
                .content("Good opinion")
                .moderated(true)
                .build();
    }

    default Review review2() {
        return Review.builder()
                .id(2L)
                .productId(2L)
                .authorName("Kamil")
                .content("Bad opinion")
                .moderated(false)
                .build();
    }

    default Review review3() {
        return Review.builder()
                .id(3L)
                .productId(3L)
                .authorName("Marek")
                .content("Nice")
                .moderated(true)
                .build();
    }

    default Review review4() {
        return Review.builder()
                .id(4L)
                .productId(4L)
                .authorName("Amelia")
                .content("Horible")
                .moderated(false)
                .build();
    }

    default Review review5() {
        return Review.builder()
                .id(5L)
                .productId(5L)
                .authorName("Justin")
                .content("Excelent")
                .moderated(true)
                .build();
    }
}

