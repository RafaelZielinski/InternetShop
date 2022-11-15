package pl.zielinski.shop.admin.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.zielinski.shop.admin.product.controller.dto.AdminProductDto;
import pl.zielinski.shop.admin.product.model.AdminProduct;
import pl.zielinski.shop.admin.product.service.AdminProductService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AdminProductController {

    private static final Long EMPTY_ID = null;
    private final AdminProductService adminProductService;

    @GetMapping("/admin/products")
    public Page<AdminProduct> getProducts(Pageable pageable) {
        return adminProductService.getProducts(pageable);
    }

    @GetMapping("/admin/products/{id}")
    public AdminProduct getProducts(@PathVariable("id") Long id) {
        return adminProductService.getProducts(id);
    }

    @PostMapping("/admin/products")
    public AdminProduct createProduct(@RequestBody @Valid AdminProductDto adminProductDto) {
        return adminProductService.createProduct(mapAdminProduct(adminProductDto, EMPTY_ID));
    }

    @PutMapping ("/admin/products/{id}")
    public AdminProduct updateProduct(@RequestBody @Valid AdminProductDto adminProductDto, @PathVariable("id") Long id ) {
        return adminProductService.updateProduct(mapAdminProduct(adminProductDto, id));
    }

    @DeleteMapping ("/admin/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id ) {
        adminProductService.deleteProduct(id);
    }

    private AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.getName())
                .description(adminProductDto.getDescription())
                .category(adminProductDto.getCategory())
                .price(adminProductDto.getPrice())
                .currency(adminProductDto.getCurrency())
                .build();
    }

}
