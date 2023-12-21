package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import g26.eDucaApp.Model.S_class;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    Optional<Student> findByEmail(String email);

    Optional<Student> findByName(String name);

    Optional<Student> findByNmec(Long nmec);

    List<Student> findByStudentclass(S_class studentclass);

    List<Student> findBySchool(String school);

}
