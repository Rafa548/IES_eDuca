package g26.eDucaApp.Services;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Sch_Admin;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Model.Sys_Admin;
import g26.eDucaApp.Repository.*;

import lombok.AllArgsConstructor;
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

    public List<Student> getStudentByS_class(S_class s_class) {
        List<Student> students = std_repo.findByStudentclass(s_class);

        if (students.isEmpty()) {
            return null;
        } else {
            return students;
        }
    }

    public List<Student> getStudentBySchool(String school) {
        List<Student> students = std_repo.findBySchool(school);

        if (students.isEmpty()) {
            return null;
        } else {
            return students;
        }
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

    public List<Teacher> getTeacherBySchool(String school) {
        List<Teacher> teachers = teacher_repo.findBySchool(school);

        if (teachers.isEmpty()) {
            return null;
        } else {
            return teachers;
        }
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

    private S_ClassRepository s_class_repo;

    public S_class createS_class(S_class s_class) {
        if (s_class_repo.findByClassname(s_class.getClassname()).isPresent()) {
            return null;
        }
        return s_class_repo.save(s_class);
    }

    public S_class getS_classByClassname(String classname) {
        if (s_class_repo.findByClassname(classname).isEmpty()) {
            return null;
        }
        return s_class_repo.findByClassname(classname).get();
    }

    public List<S_class> getAllS_class() {

        return s_class_repo.findAll();
    }

    public void deleteS_class(String classname){
        if (s_class_repo.findByClassname(classname).isEmpty()) {
            return;
        }
        S_class s_class = s_class_repo.findByClassname(classname).get();
        s_class_repo.delete(s_class);
    }

    public S_class updateS_class(S_class s_class){
        S_class existingS_class = s_class_repo.findById(s_class.getId()).get();
        existingS_class.setClassname(s_class.getClassname());
        existingS_class.setStudents(s_class.getStudents());
        S_class updatedS_class = s_class_repo.save(existingS_class);
        return updatedS_class;
    }

    private Sch_AdminRepository sch_admin_repo;

    public Sch_Admin createSch_Admin(Sch_Admin sch_admin) {
        if (sch_admin_repo.findById(sch_admin.getId()).isPresent()) {
            return null;
        }
        return sch_admin_repo.save(sch_admin);
    }

    public Sch_Admin getSch_AdminByEmail(String sch_adminEmail) {
        if (sch_admin_repo.findByEmail(sch_adminEmail).isEmpty()) {
            return null;
        }
        return sch_admin_repo.findByEmail(sch_adminEmail).get();
    }

    public Sch_Admin getSch_AdminByName(String sch_adminName) {
        if (sch_admin_repo.findByName(sch_adminName).isEmpty()) {
            return null;
        }
        return sch_admin_repo.findByName(sch_adminName).get();
    }

    public Sch_Admin getSch_AdminBySchool(String school) {
        if (sch_admin_repo.findBySchool(school).isEmpty()) {
            return null;
        }
        return sch_admin_repo.findBySchool(school).get();
    }

    public List<Sch_Admin> getAllSch_Admin() {
        if (sch_admin_repo.findAll().isEmpty()) {
            return null;
        }
        return sch_admin_repo.findAll();
    }

    public void deleteSch_Admin(Long id) {
        if (sch_admin_repo.findById(id).isEmpty()) {
            return;
        }
        Sch_Admin sch_admin = sch_admin_repo.findById(id).get();
        sch_admin_repo.delete(sch_admin);
    }

    public Sch_Admin updateSch_Admin(Sch_Admin sch_admin) {
        Sch_Admin existingSch_Admin = sch_admin_repo.findById(sch_admin.getId()).get();
        existingSch_Admin.setName(sch_admin.getName());
        existingSch_Admin.setEmail(sch_admin.getEmail());
        existingSch_Admin.setPassword(sch_admin.getPassword());
        existingSch_Admin.setSchool(sch_admin.getSchool());
        Sch_Admin updatedSch_Admin = sch_admin_repo.save(existingSch_Admin);
        return updatedSch_Admin;
    }

    private SubjectRepository subject_repo;

    public Subject createSubject(Subject subject) {
        if (subject_repo.findById(subject.getId()).isPresent()) {
            return null;
        }
        return subject_repo.save(subject);
    }

    public Subject getSubjectByName(String subjectName) {
        if (subject_repo.findByName(subjectName).isEmpty()) {
            return null;
        }
        return subject_repo.findByName(subjectName).get();
    }

    public List<Subject> getAllSubjects() {
        if (subject_repo.findAll().isEmpty()) {
            return null;
        }
        return subject_repo.findAll();
    }

    public void deleteSubject(Long id) {
        if (subject_repo.findById(id).isEmpty()) {
            return;
        }
        Subject subject = subject_repo.findById(id).get();
        subject_repo.delete(subject);
    }

    public Subject getSubjectByTeacher(Teacher teacher) {
        if (subject_repo.findByTeacher(teacher).isEmpty()) {
            return null;
        }
        return subject_repo.findByTeacher(teacher).get();
    }

    private Sys_AdminRepository sys_admin_repo;

    public Sys_Admin createSys_Admin(Sys_Admin sys_admin) {
        if (sys_admin_repo.findById(sys_admin.getId()).isPresent()) {
            return null;
        }
        return sys_admin_repo.save(sys_admin);
    }

    public Sys_Admin getSys_AdminByEmail(String sys_adminEmail) {
        if (sys_admin_repo.findByEmail(sys_adminEmail).isEmpty()) {
            return null;
        }
        return sys_admin_repo.findByEmail(sys_adminEmail).get();
    }

    public Sys_Admin getSys_AdminByName(String sys_adminName) {
        if (sys_admin_repo.findByName(sys_adminName).isEmpty()) {
            return null;
        }
        return sys_admin_repo.findByName(sys_adminName).get();
    }

    public List<Sys_Admin> getAllSys_Admin() {
        if (sys_admin_repo.findAll().isEmpty()) {
            return null;
        }
        return sys_admin_repo.findAll();
    }

    public void deleteSys_Admin(Long id) {
        if (sys_admin_repo.findById(id).isEmpty()) {
            return;
        }
        Sys_Admin sys_admin = sys_admin_repo.findById(id).get();
        sys_admin_repo.delete(sys_admin);
    }

    public Sys_Admin updateSys_Admin(Sys_Admin sys_admin) {
        Sys_Admin existingSys_Admin = sys_admin_repo.findById(sys_admin.getId()).get();
        existingSys_Admin.setName(sys_admin.getName());
        existingSys_Admin.setEmail(sys_admin.getEmail());
        existingSys_Admin.setPassword(sys_admin.getPassword());
        Sys_Admin updatedSys_Admin = sys_admin_repo.save(existingSys_Admin);
        return updatedSys_Admin;
    }



}
