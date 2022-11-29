package pl.zielinski.shop.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zielinski.shop.category.model.Category;
import pl.zielinski.shop.category.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
