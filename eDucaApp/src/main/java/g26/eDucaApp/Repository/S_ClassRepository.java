package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.S_class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface S_ClassRepository extends JpaRepository<S_class, Long>{
    Optional<S_class> findByName(String name);
}
