package g26.eDucaApp.Services;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Repository.StudentRepository;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Repository.Sys_AdminRepository;
import g26.eDucaApp.Repository.TeacherRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class EducaServices {

    private StudentRepository std_repo;

    public Student createStudent(Student student) {
        return std_repo.save(student);
    }

    public Student getStudentByEmail(String studentEmail) {
        if (std_repo.findByEmail(studentEmail).isEmpty()) {
            return null;
        }
        return std_repo.findByEmail(studentEmail).get();
    }

    public Student getStudentByName(String studentName) {
        if (std_repo.findByName(studentName).isEmpty()) {
            return null;
        }
        return std_repo.findByName(studentName).get();
    }

    public Student getStudentByNmec(Long studentN_mec) {
        if (std_repo.findByNmec(studentN_mec).isEmpty()) {
            return null;
        }
        return std_repo.findByNmec(studentN_mec).get();
    }

    public Student getStudentByS_class(S_class s_class) {
        if (std_repo.findByStudentclass(s_class).isEmpty()) {
            return null;
        }
        return std_repo.findByStudentclass(s_class).get();
    }

    public List<Student> getAllStudents() {
        return std_repo.findAll();
    }

    public Student updateStudent(Student student){
        Student existingStudent = std_repo.findById(student.getId()).get();
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setNmec(student.getNmec());
        existingStudent.setStudentclass(student.getStudentclass());
        Student updatedStudent = std_repo.save(existingStudent);
        return updatedStudent;
    }

    public void deleteStudent(Long n_mec) {
        if (std_repo.findByNmec(n_mec).isEmpty()) {
            return;
        }
        Student student = std_repo.findByNmec(n_mec).get();
        std_repo.delete(student);
    }



    private TeacherRepository teacher_repo;

    public Teacher createTeacher(Teacher teacher) {
        if (teacher_repo.findByNmec(teacher.getNmec()).isPresent()) {
            return null;
        }
        return teacher_repo.save(teacher);
    }

    public Teacher getTeacherByEmail(String teacherEmail) {
        if (teacher_repo.findByEmail(teacherEmail).isEmpty()) {
            return null;
        }
        return teacher_repo.findByEmail(teacherEmail).get();
    }


    public Teacher getTeacherByName(String teacherName) {
        if (teacher_repo.findByName(teacherName).isEmpty()) {
            return null;
        }
        return teacher_repo.findByName(teacherName).get();
    }

    public Teacher getTeacherByN_mec(Long teacherN_mec) {
        if (teacher_repo.findByNmec(teacherN_mec).isEmpty()) {
            return null;
        }
        return teacher_repo.findByNmec(teacherN_mec).get();
    }

    public Teacher getTeacherBySchool(String school) {
        if (teacher_repo.findBySchool(school).isEmpty()) {
            return null;
        }
        return teacher_repo.findBySchool(school).get();
    }

    public List<Teacher> getAllTeachers() {
        if (teacher_repo.findAll().isEmpty()) {
            return null;
        }
        return teacher_repo.findAll();
    }


    public void deleteTeacher(Long n_mec) {
        if (teacher_repo.findByNmec(n_mec).isEmpty()) {
            return;
        }
        Teacher teacher = teacher_repo.findByNmec(n_mec).get();
        teacher_repo.delete(teacher);
    }




}
