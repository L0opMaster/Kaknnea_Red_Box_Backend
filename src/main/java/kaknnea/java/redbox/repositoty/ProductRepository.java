package kaknnea.java.redbox.repositoty;

import kaknnea.java.redbox.entity.Product;
import kaknnea.java.redbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);

    Optional<Product> findByIdAndUser(Long id, User user);
}
