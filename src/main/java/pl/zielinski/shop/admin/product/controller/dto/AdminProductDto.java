package pl.zielinski.shop.admin.product.controller.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import pl.zielinski.shop.admin.product.model.AdminProductCurrency;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
public class AdminProductDto {
    @NotBlank
    @Length(min = 4)
    private String name;
    @NotNull
    @Min(value = 1)
    private Long categoryId;
    @NotBlank
    @Length(min = 4)
    private String description;
    private String fullDescription;
    @Min(0)
    @NotNull
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AdminProductCurrency currency;
    private String image;
    @NotBlank
    @Length(min = 4)
    private String slug;
}
