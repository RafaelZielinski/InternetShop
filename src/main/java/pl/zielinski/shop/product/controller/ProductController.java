package pl.zielinski.shop.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zielinski.shop.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {
    @GetMapping("/products")
    public List<Product> getProducts() {
        return List.of(
                new Product("Gąbka", "wodne", "wchłania się", new BigDecimal("8.99"), "PLN"),
                new Product("Klocek", "wood", "rzuca", new BigDecimal("4.22"), "EUR"),
                new Product("Lalka", "toy", "bawi się", new BigDecimal("6.68"), "USD"),
                new Product("Car", "race", "jeździ", new BigDecimal("3.43"), "RUB"));
    }


}
