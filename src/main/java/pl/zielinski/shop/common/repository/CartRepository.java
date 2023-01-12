package pl.zielinski.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zielinski.shop.common.model.Cart;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCreatedLessThan(LocalDateTime Days);

    @Query("delete from Cart c where c.id in (:ids)")
    @Modifying
    void deleteAllByIdIn(List<Long> ids);
}
