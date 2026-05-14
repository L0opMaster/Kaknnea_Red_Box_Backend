package kaknnea.java.redbox.controller;

import jakarta.validation.Valid;
import kaknnea.java.redbox.dto.CategoryDtoRequest;
import kaknnea.java.redbox.dto.CategoryDtoResponse;
import kaknnea.java.redbox.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDtoResponse> addCategory(@Valid @RequestBody CategoryDtoRequest categoryDtoRequest) {
        CategoryDtoResponse addCategory = categoryService.addCategory(categoryDtoRequest);

        return new ResponseEntity<>(addCategory,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDtoResponse> getCategoryById(@PathVariable Long id){
        CategoryDtoResponse categoryDtoResponse = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDtoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<CategoryDtoResponse>> getAllCategory(){
        List<CategoryDtoResponse> categoryDtoResponse = categoryService.getAllCategory();

        return new ResponseEntity<>(categoryDtoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDtoResponse> updateCategory(@PathVariable Long id,
                                                              @Valid @RequestBody CategoryDtoRequest categoryDtoRequest) {
        CategoryDtoResponse categoryDtoResponse = categoryService.updateCategory(id, categoryDtoRequest);

        return new ResponseEntity<>(categoryDtoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletedById(@PathVariable("id") Long id) {
        categoryService.deletedById(id);

        return new ResponseEntity<>("Deleted category with id: "+id+" successfully", HttpStatus.OK);
    }
}
