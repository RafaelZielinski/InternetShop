package pl.zielinski.shop.admin.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zielinski.shop.admin.review.model.AdminReview;
import pl.zielinski.shop.admin.review.service.AdminReviewService;
import pl.zielinski.shop.common.model.Review;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reviews")
public class AdminReviewController {
    private final AdminReviewService adminReviewService;

    @GetMapping
    public List<AdminReview> getReviews(){
        return adminReviewService.getReviews();

    }

    @PutMapping("/{id}/moderate")
    public void moderate(@PathVariable Long id) {
        adminReviewService.moderate(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminReviewService.delete(id);
    }



}
