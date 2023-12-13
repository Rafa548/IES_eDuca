package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByNmec(Long nmec);
    List<Teacher> findBySchool(String school);

    Optional<Teacher> findByName(String name);

    Optional<Teacher> findByEmail(String email);

    List<Teacher> findBySubjectsName(String subjectName);

}
