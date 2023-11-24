package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import g26.eDucaApp.Model.S_class;

public interface StudentRepository extends JpaRepository<Student, Long>{
    Optional<Student> findByEmail(String email);

    Optional<Student> findByName(String name);

    Optional<Student> findByNmec(Long nmec);

    Optional<Student> findByStudentclass(S_class studentclass);

}
