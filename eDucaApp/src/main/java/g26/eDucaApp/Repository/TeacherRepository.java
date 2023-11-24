package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByN_Mec(long n_mec);
    Optional<Teacher> findBySchool(String school);

    Optional<Teacher> findByName(String name);

    Optional<Teacher> findByEmail(String email);

}
