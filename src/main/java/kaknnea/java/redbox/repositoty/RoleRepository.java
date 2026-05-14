package kaknnea.java.redbox.repositoty;

import kaknnea.java.redbox.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
}
