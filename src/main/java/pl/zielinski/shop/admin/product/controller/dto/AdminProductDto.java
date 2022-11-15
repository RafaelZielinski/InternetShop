package pl.zielinski.shop.admin.product.controller.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import pl.zielinski.shop.admin.product.model.AdminProductCurrency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
public class AdminProductDto {
    @NotBlank
    @Length(min = 4)
    private String name;
    @NotBlank
    @Length(min = 4)
    private String category;
    @NotBlank
    @Length(min = 4)
    private String description;
    @NotBlank
    @Min(0)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private AdminProductCurrency currency;
}
