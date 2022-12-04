package pl.zielinski.shop.admin.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.admin.review.model.AdminReview;
import pl.zielinski.shop.admin.review.repository.AdminReviewRepository;
import pl.zielinski.shop.common.model.Review;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
    private final AdminReviewRepository adminReviewRepository;


    public List<AdminReview> getReviews() {
        return adminReviewRepository.findAll();
    }

    public void delete(Long id) {
        adminReviewRepository.deleteById(id);
    }

    @Transactional
    public void moderate(Long id) {
        adminReviewRepository.moderate(id);

    }
}
