package pl.zielinski.shop.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.common.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
