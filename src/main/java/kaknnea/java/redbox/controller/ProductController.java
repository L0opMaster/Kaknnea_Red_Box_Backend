package kaknnea.java.redbox.controller;

import jakarta.validation.Valid;
import kaknnea.java.redbox.dto.ProductDtoRequest;
import kaknnea.java.redbox.dto.ProductDtoResponse;
import kaknnea.java.redbox.service.ProductService;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/search")
    public Page<ProductDtoResponse> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return productService.search(
                q,
                categoryId,
                active,
                pageable
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/my-product")
    public Page<ProductDtoResponse> searchMyProduct(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            ){
        Pageable pageable = PageRequest.of(page, size);

        return productService.searchMyProduct(
                q,
                categoryId,
                active,
                pageable
        );
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDtoRequest request) {
        String response = productService.addProduct(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDtoResponse>> gatAllProduct(){
        List<ProductDtoResponse> responses = productService.getAllProduct();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/my-products")
    public ResponseEntity<List<ProductDtoResponse>> getMyProduct(){
        List<ProductDtoResponse> response = productService.getMyProduct();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDtoResponse> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDtoRequest request){
        ProductDtoResponse response = productService.updateMyProduct(id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMyProduct(@PathVariable("id") Long proId){
        String response = productService.deleteMyProduct(proId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
