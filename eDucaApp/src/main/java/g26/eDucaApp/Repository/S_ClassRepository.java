package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface S_ClassRepository extends JpaRepository<S_class, Long>{
    Optional<S_class> findByClassname(String classname);


    List<S_class> findByStudentsContaining(Student student);
}
