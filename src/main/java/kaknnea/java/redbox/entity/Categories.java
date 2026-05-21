package kaknnea.java.redbox.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "khmer_name is required")
    @Column(name = "khmer_name", length = 100, nullable = false, unique = true)
    private String khmerName;

    @NotBlank(message = "english_name is required")
    @Column(name = "english_name", length = 100, nullable = false, unique = true)
    private String englishName;

    private boolean active;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Categories(){};

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public Categories(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, List<Product> products, boolean active, String englishName, String khmerName) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.products = products;
        this.active = active;
        this.englishName = englishName;
        this.khmerName = khmerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
}
