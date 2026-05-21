package kaknnea.java.redbox.service;

import kaknnea.java.redbox.dto.ProductDtoRequest;
import kaknnea.java.redbox.dto.ProductDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    String addProduct(ProductDtoRequest productDtoRequest);

    // Get All Product for admin
    List<ProductDtoResponse> getAllProduct();

    // Get Product By UserId
    List<ProductDtoResponse> getMyProduct();

    // UpdateProduct
    ProductDtoResponse updateMyProduct(Long id, ProductDtoRequest request);

    // Delete Product
    String deleteMyProduct(Long proId);

    //Public SearchProduct
    Page<ProductDtoResponse> search(
            String q,
            Long categoryId,
            Boolean active,
            Pageable pageable
    );

    //Private Search for owner product
    Page<ProductDtoResponse> searchMyProduct(
            String q,
            Long categoryId,
            Boolean active,
            Pageable pageable
    );
}
