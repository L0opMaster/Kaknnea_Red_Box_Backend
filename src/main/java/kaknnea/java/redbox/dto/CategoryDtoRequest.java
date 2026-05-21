package kaknnea.java.redbox.dto;


import jakarta.validation.constraints.NotBlank;

public class CategoryDtoRequest {

    @NotBlank(message = "khmer_name is required")
    private String khmerName;
    @NotBlank(message = "english_name is required")
    private String englishName;


    public CategoryDtoRequest(){}
    public CategoryDtoRequest(String khmerName, String englishName) {
        this.khmerName = khmerName;
        this.englishName = englishName;
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
