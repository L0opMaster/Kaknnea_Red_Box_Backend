package kaknnea.java.redbox.dto;

import java.time.LocalDateTime;

public class RoleDtoResponse {

    private Long id;
    private String name;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoleDtoResponse(Long id, LocalDateTime updatedAt, LocalDateTime createdAt, boolean isActive, String name) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.isActive = isActive;
        this.name = name;
    }
    public RoleDtoResponse(){}

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
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
