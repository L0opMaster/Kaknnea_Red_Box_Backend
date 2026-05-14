package kaknnea.java.redbox.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CategoryDtoResponse {

    private Long id;
    @NotBlank(message = "khmer_name is required")
    private String khmerName;
    @NotBlank(message = "english_name is required")
    private String englishName;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CategoryDtoResponse(Long id, LocalDateTime updatedAt, LocalDateTime createdAt,
                               boolean active, String englishName, String khmerName
    ) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.active = active;
        this.englishName = englishName;
        this.khmerName = khmerName;
    }

    public CategoryDtoResponse(){}

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
