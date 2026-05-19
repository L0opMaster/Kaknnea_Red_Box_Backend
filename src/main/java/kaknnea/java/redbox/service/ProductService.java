package kaknnea.java.redbox.service;

import kaknnea.java.redbox.dto.ProductDtoRequest;
import kaknnea.java.redbox.dto.ProductDtoResponse;

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
}
