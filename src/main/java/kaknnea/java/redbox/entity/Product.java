package kaknnea.java.redbox.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "product_code is required")
    @Column(name = "product_code", nullable = false, length = 250)
    private String productCode;

    @NotBlank(message = "khmer_name is required")
    @Column(name = "khmer_name", nullable = false, length = 250, unique = false)
    private String khmerName;

    @NotBlank(message = "english_name is required")
    @Column(name = "english_name", nullable = false, length = 250, unique = false)
    private String englishName;

    @Column(length = 500)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @NotBlank(message = "image_url is required")
    @Column(name = "image_url", nullable = false, length = 500, unique = false)
    private String imageUrl;

    @NotNull(message = "price is required")
    @Column(nullable = false, precision = 18, scale = 2, unique = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product() {}

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Product(Long id, String productCode, String khmerName, String englishName, String description, Boolean isActive, String imageUrl, BigDecimal price, Categories category, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productCode = productCode;
        this.khmerName = khmerName;
        this.englishName = englishName;
        this.description = description;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
// GETTER & SETTER


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public String getKhmerName() {
        return khmerName;
    }

    public void setKhmerName(String khmerName) {
        this.khmerName = khmerName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}