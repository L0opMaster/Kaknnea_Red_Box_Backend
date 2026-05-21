package kaknnea.java.redbox.mapper;

import kaknnea.java.redbox.dto.ProductDtoRequest;
import kaknnea.java.redbox.dto.ProductDtoResponse;
import kaknnea.java.redbox.entity.Product;

public class ProductModelMapper {

    public static ProductDtoResponse mapToProductResponse(Product product){
        ProductDtoResponse response = new ProductDtoResponse();

        response.setId(product.getId());
        response.setProductCode(product.getProductCode());
        response.setEnglishName(product.getEnglishName());
        response.setKhmerName(product.getKhmerName());
        response.setActive(product.getActive());
        response.setUser(product.getUser().getId());
        response.setCategory(product.getCategory().getId());
        response.setImageUrl(response.getImageUrl());
        response.setPrice(response.getPrice());
        response.setCreatedAt(response.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        response.setDescription(product.getDescription());
        return response;
    }
}
