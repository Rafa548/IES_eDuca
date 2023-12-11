package g26.eDucaApp.Repository;
import g26.eDucaApp.Model.Grade;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long>{

    List<Grade> findByStudent(Student student);

    List<Grade> findBySubject(Subject subject);

    List<Grade> findByTeacher(Teacher teacher);

    List<Grade> findByStudentAndSubject(Student student, Subject subject);

}
