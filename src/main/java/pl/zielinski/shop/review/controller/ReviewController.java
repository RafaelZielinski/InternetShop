package pl.zielinski.shop.review.controller;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zielinski.shop.review.controller.dto.ReviewDto;
import pl.zielinski.shop.common.model.Review;
import pl.zielinski.shop.review.service.ReviewService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto) {
        return reviewService.addReview(Review.builder()
                .authorName(cleanContent(reviewDto.authorName()))
                .content(reviewDto.content())
                .productId(reviewDto.productId())
                .build());
    }
    private String cleanContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }
}
