package kaknnea.java.redbox.repositoty;

import kaknnea.java.redbox.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository
        extends JpaRepository<Categories, Long> {

    boolean existsByKhmerName(String khmerName);

    boolean existsByEnglishName(String englishName);
}
