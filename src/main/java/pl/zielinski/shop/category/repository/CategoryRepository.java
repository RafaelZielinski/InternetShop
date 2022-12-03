package pl.zielinski.shop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.common.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    @Query("select c from Category c " +
//            "left join fetch c.product " +
//            "where c.slug=:slug")
    Category findBySlug(String slug);



}
