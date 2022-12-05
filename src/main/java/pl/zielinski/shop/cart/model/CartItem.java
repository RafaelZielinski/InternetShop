package pl.zielinski.shop.cart.model;

import lombok.*;
import pl.zielinski.shop.common.model.Product;

import javax.persistence.*;

@Setter
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @OneToOne
    private Product product;
    private Long cartId;
}
