package kaknnea.java.redbox.service;

import kaknnea.java.redbox.dto.CategoryDtoRequest;
import kaknnea.java.redbox.dto.CategoryDtoResponse;

import java.util.List;

public interface CategoryService {

    CategoryDtoResponse addCategory(CategoryDtoRequest categoryDtoRequest);

    CategoryDtoResponse getCategoryById(Long id);

    List<CategoryDtoResponse> getAllCategory();

    CategoryDtoResponse updateCategory(Long id, CategoryDtoRequest categoryDtoRequest);

    void deletedById(Long id);
}
