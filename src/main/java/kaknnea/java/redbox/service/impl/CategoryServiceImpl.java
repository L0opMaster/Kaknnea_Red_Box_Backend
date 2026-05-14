package kaknnea.java.redbox.service.impl;

import kaknnea.java.redbox.dto.CategoryDtoRequest;
import kaknnea.java.redbox.dto.CategoryDtoResponse;
import kaknnea.java.redbox.entity.Categories;
import kaknnea.java.redbox.exception.APIException;
import kaknnea.java.redbox.repositoty.CategoryRepository;
import kaknnea.java.redbox.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDtoResponse addCategory(CategoryDtoRequest categoryDtoRequest) {
        boolean isExistByKhmerName = categoryRepository
                .existsByKhmerName(categoryDtoRequest.getKhmerName());
        boolean isExistByEnglishName = categoryRepository
                .existsByEnglishName(categoryDtoRequest.getEnglishName());
        if (isExistByKhmerName) {
            throw new APIException(
                    HttpStatus.CONFLICT,
                    "This Category with "+ categoryDtoRequest.getKhmerName() + " already exist"
            );
        }
        if (isExistByEnglishName) {
            throw new APIException(
                    HttpStatus.CONFLICT,
                    "This Category with "+ categoryDtoRequest.getEnglishName() + " already exist"
            );
        }
        Categories categories = modelMapper.map(categoryDtoRequest, Categories.class);
        Categories savedCategory = categoryRepository.save(categories);
        return modelMapper.map(savedCategory, CategoryDtoResponse.class);
    }

    @Override
    public CategoryDtoResponse getCategoryById(Long id) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Category not found with Id: " +id));
        return modelMapper.map(categories, CategoryDtoResponse.class);
    }

    @Override
    public List<CategoryDtoResponse> getAllCategory() {
        List<Categories> categoryDtoResponses = categoryRepository.findAll();

        return categoryDtoResponses.stream()
                .map((category) -> modelMapper.map(category, CategoryDtoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDtoResponse updateCategory(Long id, CategoryDtoRequest categoryDtoRequest) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Category not found with Id: "+id));
        categories.setKhmerName(categoryDtoRequest.getKhmerName());
        categories.setEnglishName(categories.getEnglishName());
        categories.setActive(true);

        Categories savedCategory = categoryRepository.save(categories);
        return modelMapper.map(savedCategory, CategoryDtoResponse.class);
    }

    @Override
    public void deletedById(Long id) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Category not found with Id: "+id));
        
        categoryRepository.deleteById(id);
    }
}
