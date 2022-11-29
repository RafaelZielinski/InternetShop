package pl.zielinski.shop.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.category.model.Category;
import pl.zielinski.shop.category.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
