package pl.zielinski.shop.admin.product.controller;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.zielinski.shop.admin.product.controller.dto.AdminProductDto;
import pl.zielinski.shop.admin.product.controller.dto.UploadResponse;
import pl.zielinski.shop.admin.product.model.AdminProduct;
import pl.zielinski.shop.admin.product.service.AdminProductImageService;
import pl.zielinski.shop.admin.product.service.AdminProductService;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static pl.zielinski.shop.admin.common.utils.SlugifyUtils.slugifySlug;


@RestController
@RequiredArgsConstructor
public class AdminProductController {

    private static final Long EMPTY_ID = null;
    private final AdminProductService adminProductService;
    private final AdminProductImageService adminProductImageService;

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

    @PostMapping("/admin/products/upload-image")
    public UploadResponse uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        try(InputStream inputStream = multipartFile.getInputStream()) {
            String savedFilename = adminProductImageService.uploadImage(multipartFile.getOriginalFilename(), inputStream);
             return new UploadResponse(savedFilename);
        } catch (IOException exception) {
            throw new RuntimeException("coś poszło źle podczas wgrywanie pliku", exception)  ;
        }
    }

    @GetMapping("/data/productImage/{filename}")
    public ResponseEntity<Resource> serveFiles(@PathVariable(name = "filename") String fileName) throws IOException {
       Resource file = adminProductImageService.serverFiles(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(fileName)))
                .body(file);
    }
    private AdminProduct mapAdminProduct(AdminProductDto adminProductDto, Long id) {
        return AdminProduct.builder()
                .id(id)
                .name(adminProductDto.getName())
                .description(adminProductDto.getDescription())
                .fullDescription(adminProductDto.getFullDescription())
                .categoryId(adminProductDto.getCategoryId())
                .price(adminProductDto.getPrice())
                .currency(adminProductDto.getCurrency())
                .image(adminProductDto.getImage())
                .slug(slugifySlug(adminProductDto.getSlug()))
                .build();
    }



}
