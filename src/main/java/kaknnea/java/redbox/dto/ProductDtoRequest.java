package kaknnea.java.redbox.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductDtoRequest {


    @NotBlank(message = "khmer_name is required")
    private String khmerName;

    @NotBlank(message = "english_name is required")
    private String englishName;

    private String description;

    @NotBlank(message = "image_url is required")
    private String imageUrl;

    private Boolean isActive;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @NotNull(message = "category_id is required")
    private Long categoryId;

    public ProductDtoRequest() {
    }

    public ProductDtoRequest(String khmerName, Long categoryId, BigDecimal price, String imageUrl, String description, String englishName, Boolean isActive) {
        this.khmerName = khmerName;
        this.categoryId = categoryId;
        this.price = price;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.description = description;
        this.englishName = englishName;
    }

    public String getKhmerName() {
        return khmerName;
    }

    public void setKhmerName(String khmerName) {
        this.khmerName = khmerName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}