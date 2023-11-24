package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
    Optional<Subject> findByName(String name);

}
