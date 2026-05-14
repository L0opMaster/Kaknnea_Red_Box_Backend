package kaknnea.java.redbox.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

public class RoleDtoRequest {

    private Long id;
    @NotBlank(message = "Role name is required")
    @Pattern(
            regexp = "^[A-Z_]+$",
            message = "Role title can contain only UPPERCASE letters and underscore"
    )
    private String name;
    private boolean active;

    public RoleDtoRequest(Long id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }
    public RoleDtoRequest(){}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
