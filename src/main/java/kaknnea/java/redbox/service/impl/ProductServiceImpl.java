package kaknnea.java.redbox.service.impl;

import kaknnea.java.redbox.dto.ProductDtoRequest;
import kaknnea.java.redbox.dto.ProductDtoResponse;
import kaknnea.java.redbox.entity.Categories;
import kaknnea.java.redbox.entity.Product;
import kaknnea.java.redbox.entity.User;
import kaknnea.java.redbox.exception.APIException;
import kaknnea.java.redbox.mapper.ProductModelMapper;
import kaknnea.java.redbox.repositoty.CategoryRepository;
import kaknnea.java.redbox.repositoty.ProductRepository;
import kaknnea.java.redbox.repositoty.UserRepository;
import kaknnea.java.redbox.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,
                              UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository =categoryRepository;
    }

    @Override
    public String addProduct(ProductDtoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        assert authentication != null;
        assert authentication != null;
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST,"User not found"));

        Categories categories = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST,"Category not found"));
        String productCode = "PROD-" + UUID.randomUUID().toString().substring(0, 8);
        Product product = new Product();
        product.setProductCode(productCode);
        product.setKhmerName(request.getKhmerName());
        product.setEnglishName(request.getEnglishName());
        product.setActive(request.getActive());
        product.setImageUrl(request.getImageUrl());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(categories);
        product.setUser(user);
        Product saveProduct = productRepository.save(product);
        return "Create Product Success!";
    }

    @Override
    public List<ProductDtoResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();


        return products.stream().map((product) -> {
            ProductDtoResponse response = new ProductDtoResponse();

            response.setId(product.getId());
            response.setProductCode(product.getProductCode());
            response.setKhmerName(product.getKhmerName());
            response.setEnglishName(product.getEnglishName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setImageUrl(product.getImageUrl());
            response.setActive(product.getActive());


            // categoryId
            response.setCategory(product.getCategory().getId());

            // user Id
            response.setUser(product.getUser().getId());

            response.setCreatedAt(product.getCreatedAt());
            response.setUpdatedAt(product.getUpdatedAt());

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDtoResponse> getMyProduct() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : null;

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User not found"));

        List<Product> products = productRepository.findByUserId(user.getId());
//        return products.stream().map(
//                product -> {
//                    ProductDtoResponse response = new ProductDtoResponse();
//                    response.setId(product.getId());
//                    response.setProductCode(product.getProductCode());
//                    response.setKhmerName(product.getKhmerName());
//                    response.setEnglishName(product.getEnglishName());
//                    response.setDescription(product.getDescription());
//                    response.setActive(product.isActive());
//                    response.setImageUrl(product.getImageUrl());
//                    response.setPrice(product.getPrice());
//
//                    response.setCategory(product.getCategory().getId());
//                    response.setUser(product.getUser().getId());
//
//                    response.setCreatedAt(product.getCreatedAt());
//                    response.setUpdatedAt(product.getUpdatedAt());
//
//                    return response;
//                }
//        ).collect(Collectors.toList());

        List<ProductDtoResponse> list = new ArrayList<>();
        for (Product product : products) {
            ProductDtoResponse response = ProductModelMapper.mapToProductResponse(product);
            list.add(response);
        }
        return list;
    }

    @Override
    public ProductDtoResponse updateMyProduct(Long id, ProductDtoRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication != null ? authentication.getName() : null;
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new APIException( HttpStatus.NOT_FOUND, "User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found"));

        // Check you are owner of product or not
        if (!product.getUser().getId().equals(user.getId())){
            throw new APIException(
                    HttpStatus.FORBIDDEN,
                    "You can't update this product"
            );
        }

        Categories categories = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Category not found"));

        // Update data
        product.setKhmerName(request.getKhmerName());
        product.setEnglishName(request.getEnglishName());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setActive(request.getActive());
        product.setPrice(request.getPrice());
        product.setCategory(categories);

        Product updatedProduct = productRepository.save(product);

        ProductDtoResponse response = new ProductDtoResponse();

        response.setId(updatedProduct.getId());
        response.setProductCode(updatedProduct.getProductCode());
        response.setKhmerName(updatedProduct.getKhmerName());
        response.setEnglishName(updatedProduct.getEnglishName());
        response.setDescription(updatedProduct.getDescription());
        response.setImageUrl(updatedProduct.getImageUrl());
        response.setActive(updatedProduct.getActive());
        response.setPrice(updatedProduct.getPrice());
        response.setCategory(updatedProduct.getCategory().getId());
        response.setUser(updatedProduct.getUser().getId());
        response.setCreatedAt(updatedProduct.getCreatedAt());
        response.setUpdatedAt(updatedProduct.getUpdatedAt());
        return response;
    }

    @Override
    public String deleteMyProduct(Long proId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication != null ? authentication.getName() : null ;

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new APIException(HttpStatus.FORBIDDEN, "User not found"));

        Product product = productRepository.findByIdAndUser(proId, user)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Wrong productId and User"));

        productRepository.deleteById(product.getId());

        return "Deleted Successfully";
    }

    @Override
    public Page<ProductDtoResponse> search(
            String q,
            Long categoryId,
            Boolean active,
            Pageable pageable
    ) {
        Page<Product> products = productRepository.search(
                q, categoryId, active, pageable
        );
        return products.map(product -> ProductModelMapper.mapToProductResponse(product));
    }

    @Override
    public Page<ProductDtoResponse> searchMyProduct(
            String q,
            Long categoryId,
            Boolean active,
            Pageable pageable
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : null;

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new APIException(HttpStatus.FORBIDDEN, "You are not allow"));
        Page<Product> products = productRepository.searchMyProducts(
                user.getId(),
                q,
                categoryId,
                active,
                pageable
        );
        return products.map(ProductModelMapper::mapToProductResponse);
    }
}
