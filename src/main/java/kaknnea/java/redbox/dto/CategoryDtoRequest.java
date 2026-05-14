package kaknnea.java.redbox.dto;


import jakarta.validation.constraints.NotBlank;

public class CategoryDtoRequest {

    private Long id;
    @NotBlank(message = "khmer_name is required")
    private String khmerName;
    @NotBlank(message = "english_name is required")
    private String englishName;


    public CategoryDtoRequest(){}
    public CategoryDtoRequest(Long id, String khmerName, String englishName) {
        this.id = id;
        this.khmerName = khmerName;
        this.englishName = englishName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKhmerName() {
        return khmerName;
    }

    public void setKhmerName(String khmerName) {
        this.khmerName = khmerName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

}
