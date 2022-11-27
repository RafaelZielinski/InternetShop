package pl.zielinski.shop.admin.category.controller;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.zielinski.shop.admin.category.model.AdminCategory;
import pl.zielinski.shop.admin.category.service.AdminCategoryService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private static final Long EMPTY_ID = null;
    private final AdminCategoryService service;

    @GetMapping
    public List<AdminCategory> getCategories() {
        return service.getCategories();
    }

    @GetMapping("/{id}")
    public AdminCategory getCategory(@PathVariable Long id) {
        return service.getCategory(id);
    }

    @PostMapping
    public AdminCategory createCategory(@RequestBody AdminCategoryDto adminCategoryDto) {
        return service.createCategory(mapToAdminCategory(EMPTY_ID, adminCategoryDto));
    }

    @PutMapping("/{id}")
    public AdminCategory updateCategory(@PathVariable Long id, @RequestBody AdminCategoryDto adminCategoryDto) {
        return service.updateCategory(mapToAdminCategory(id, adminCategoryDto));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
    }


    private AdminCategory mapToAdminCategory(Long id, AdminCategoryDto adminCategoryDto) {
        return AdminCategory.builder()
                .id(id)
                .name(adminCategoryDto.getName())
                .description(adminCategoryDto.getDescription())
                .slug(slugifyCategoryName(adminCategoryDto.getSlug()))
                .build();

    }

    private String slugifyCategoryName(String slug) {
        Slugify slugify = new Slugify();
        return slugify.withCustomReplacement("_", "-").slugify(slug);
    }
}
