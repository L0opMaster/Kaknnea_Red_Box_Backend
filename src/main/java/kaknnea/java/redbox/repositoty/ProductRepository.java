package kaknnea.java.redbox.repositoty;

import kaknnea.java.redbox.entity.Product;
import kaknnea.java.redbox.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);

    Optional<Product> findByIdAndUser(Long id, User user);

    // PUBLIC SEARCH
    @Query("""
    select p from Product p
    where (
        lower(p.khmerName) like lower(concat('%', :q, '%'))
        or lower(p.englishName) like lower(concat('%', :q, '%'))
    )
    and (:categoryId is null or p.category.id = :categoryId)
    and (:active is null or p.isActive = :active)
    """)
    Page<Product> search(
            @Param("q") String q,
            @Param("categoryId") Long categoryId,
            @Param("active") Boolean active,
            Pageable pageable
    );

    @Query("""
        select p from Product p
        where p.user.id = :userId
        and (
           lower(p.khmerName) like lower(concat('%', :q, '%'))
           or lower(p.englishName) like lower(concat('%', :q, '%'))
        )
        and (:categoryId is null or p.category.id = :categoryId)
        and (:active is null or p.isActive = :active)
    """)
    Page<Product> searchMyProducts(
            @Param("userId") Long userId,
            @Param("q") String q,
            @Param("categoryId") Long categoryId,
            @Param("active") Boolean active,
            Pageable pageable
    );
}
