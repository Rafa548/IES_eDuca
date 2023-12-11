package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.Teaching_Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface Teaching_AssignmentRepository extends JpaRepository<Teaching_Assignment, Long>{
    List<Teaching_Assignment> findByTeacher(Teacher teacher);

    List<Teaching_Assignment> findBySclass(S_class s_class);

    List<Teaching_Assignment> findBySubject(Subject subject);

    List<Teaching_Assignment> findByTeacherAndSclass(Teacher teacher, S_class s_class);

    List<Teaching_Assignment> findByTeacherAndSubject(Teacher teacher, Subject subject);

    List<Teaching_Assignment> findBySclassAndSubject(S_class s_class, Subject subject);
}