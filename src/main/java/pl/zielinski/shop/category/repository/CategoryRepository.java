package pl.zielinski.shop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
