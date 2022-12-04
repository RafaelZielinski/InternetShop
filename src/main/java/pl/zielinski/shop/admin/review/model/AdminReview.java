package pl.zielinski.shop.admin.review.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Getter
@Setter
public class AdminReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String authorName;
    private String content;
    private boolean moderated;

}
