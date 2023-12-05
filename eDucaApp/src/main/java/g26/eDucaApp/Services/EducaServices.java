package g26.eDucaApp.Services;
import g26.eDucaApp.Model.*;
import g26.eDucaApp.Repository.*;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import g26.eDucaApp.Model.Notification;
import g26.eDucaApp.Model.NotificationType;
import g26.eDucaApp.Services.notifications.notificationsService;

@Service
@AllArgsConstructor
public class EducaServices {

    private StudentRepository std_repo;

    private TeacherRepository teacher_repo;

    private S_ClassRepository sclass_repo;

    private Sch_AdminRepository sch_admin_repo;

    private SubjectRepository subject_repo;

    private Sys_AdminRepository sys_admin_repo;

    private Teaching_AssignmentRepository teaching_assignment_repo;

    private GradeRepository grade_repo;

    // Student
    //----------------------------------------------------------------//

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

    //----------------------------------------------------------------//


    // Teacher
    //----------------------------------------------------------------//
    public Teacher createTeacher(Teacher teacher) {

        return teacher_repo.save(teacher);
    }

    public Teacher getTeacherByEmail(String teacherEmail) {
        if (teacher_repo.findByEmail(teacherEmail).isEmpty()) {
            return null;
        }
        return teacher_repo.findByEmail(teacherEmail).get();
    }

    public Teacher getTeacherById(Long id) {
        if (teacher_repo.findById(id).isEmpty()) {
            return null;
        }
        return teacher_repo.findById(id).get();
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
        return teacher_repo.findAll();
    }


    public void deleteTeacher(Long n_mec) {
        if (teacher_repo.findByNmec(n_mec).isEmpty()) {
            return;
        }
        Teacher teacher = teacher_repo.findByNmec(n_mec).get();
        teacher_repo.delete(teacher);
    }

    //----------------------------------------------------------------//


    // S_class
    public S_class createS_class(S_class s_class) {

        return sclass_repo.save(s_class);
    }

    public S_class getS_classByClassname(String classname) {
        if (sclass_repo.findByClassname(classname).isEmpty()) {
            return null;
        }
        return sclass_repo.findByClassname(classname).get();
    }

    public S_class getS_classById(Long id) {
        if (sclass_repo.findById(id).isEmpty()) {
            return null;
        }
        return sclass_repo.findById(id).get();
    }

    public List<S_class> getAllS_class() {

        return sclass_repo.findAll();
    }

    public void deleteS_class(String classname){
        if (sclass_repo.findByClassname(classname).isEmpty()) {
            return;
        }
        S_class s_class = sclass_repo.findByClassname(classname).get();
        sclass_repo.delete(s_class);
    }

    public S_class updateS_class(S_class s_class){
        S_class existingS_class = sclass_repo.findById(s_class.getId()).get();
        existingS_class.setClassname(s_class.getClassname());
        existingS_class.setStudents(s_class.getStudents());
        S_class updatedS_class = sclass_repo.save(existingS_class);
        return updatedS_class;
    }

    //----------------------------------------------------------------//


    // Sch_Admin
    //----------------------------------------------------------------//

    public Sch_Admin createSch_Admin(Sch_Admin sch_admin) {

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

    //----------------------------------------------------------------//



    // Subject
    //----------------------------------------------------------------//
    public Subject createSubject(Subject subject) {

        return subject_repo.save(subject);
    }

    public Subject getSubjectByName(String subjectName) {
        if (subject_repo.findByName(subjectName).isEmpty()) {
            return null;
        }

        return subject_repo.findByName(subjectName).get();
    }

    public Subject getSubjectById(Long id) {
        if (subject_repo.findById(id).isEmpty()) {
            return null;
        }
        return subject_repo.findById(id).get();
    }

    public List<Subject> getAllSubjects() {
        return subject_repo.findAll();
    }

    public void deleteSubject(Long id) {
        if (subject_repo.findById(id).isEmpty()) {
            return;
        }
        Subject subject = subject_repo.findById(id).get();
        subject_repo.delete(subject);
    }

    //----------------------------------------------------------------//


    // Sys_Admin
    //----------------------------------------------------------------//

    public Sys_Admin createSys_Admin(Sys_Admin sys_admin) {

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

    //----------------------------------------------------------------//


    //Teaching_Assigment
    //----------------------------------------------------------------//
    public Teaching_Assignment createTeachingAssignment(Teaching_Assignment teaching_assignment) {

        return teaching_assignment_repo.save(teaching_assignment);
    }

    public List<Teaching_Assignment> getTeachingAssignmentByTeacher(Teacher teacher) {
        List<Teaching_Assignment> teaching_assignments = teaching_assignment_repo.findByTeacher(teacher);

        if (teaching_assignments.isEmpty()) {
            return null;
        } else {
            return teaching_assignments;
        }
    }

    public List<Teaching_Assignment> getTeachingAssignmentByS_class(S_class s_class) {
        List<Teaching_Assignment> teaching_assignments = teaching_assignment_repo.findBySclass(s_class);

        if (teaching_assignments.isEmpty()) {
            return null;
        } else {
            return teaching_assignments;
        }
    }

    public List<Teaching_Assignment> getTeachingAssignmentBySubject(Subject subject) {
        List<Teaching_Assignment> teaching_assignments = teaching_assignment_repo.findBySubject(subject);

        if (teaching_assignments.isEmpty()) {
            return null;
        } else {
            return teaching_assignments;
        }
    }

    public Teaching_Assignment getTeachingAssignmentById(Long id){
        Optional<Teaching_Assignment> teaching_assignments = teaching_assignment_repo.findById(id);
        if (teaching_assignments.isEmpty()) {
            return null;
        } else {
            return teaching_assignments.get();
        }
    }

    public List<Teaching_Assignment> getAllTeachingAssignments() {

        return teaching_assignment_repo.findAll();
    }

    //----------------------------------------------------------------//

    private notificationsService notificationService;
    private NotificationRepository notificationRepository;

    //Grades
    //----------------------------------------------------------------//
    public Grade createGrade(Grade grade) {
        String message = String.format("Grade %s was added to %s by %s for subject %s", grade.getGrade(), grade.getStudent().getName(), grade.getTeacher().getName(), grade.getSubject().getName());

        Notification notification = new Notification( message , NotificationType.GRADE);
        
        Notification savedNotification = notificationRepository.save(notification);
        //System.out.println(savedNotification);
        try{
            notificationService.sendNotification(savedNotification);
            System.out.println("Notification sent");
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        

        return grade_repo.save(grade);
    }

    public List<Grade> getGradeByStudent(Student student) {
        List<Grade> grades = grade_repo.findByStudent(student);

        if (grades.isEmpty()) {
            return null;
        } else {
            return grades;
        }
    }

    public List<Grade> getGradeBySubject(Subject subject) {
        List<Grade> grades = grade_repo.findBySubject(subject);

        if (grades.isEmpty()) {
            return null;
        } else {
            return grades;
        }
    }

    public List<Grade> getGradeByTeacher(Teacher teacher) {
        List<Grade> grades = grade_repo.findByTeacher(teacher);

        if (grades.isEmpty()) {
            return null;
        } else {
            return grades;
        }
    }

    public Grade getGradeById(Long id){
        Optional<Grade> grades = grade_repo.findById(id);
        if (grades.isEmpty()) {
            return null;
        } else {
            return grades.get();
        }
    }

    public List<Grade> getAllGrades() {

        return grade_repo.findAll();
    }

    //----------------------------------------------------------------//

    public void AddStudent(JSONObject jsonObject) {
        Student student = new Student();
        student.setName(jsonObject.getString("name"));
        student.setEmail(jsonObject.getString("email"));
        student.setPassword(jsonObject.getString("password"));
        student.setNmec(jsonObject.getLong("nmec"));
        student.setSchool(jsonObject.getString("school"));

        JSONObject studentClassJson = jsonObject.getJSONObject("studentclass");
        S_class studentClass = sclass_repo.findById((long) studentClassJson.getInt("id")).get();

        if (studentClass != null) {
            student.setStudentclass(studentClass);
        }


        std_repo.save(student);
    }

    public void AddClass(JSONObject jsonObject){
        S_class studentClass = new S_class();
        studentClass.setClassname(jsonObject.getString("classname"));
        studentClass.setSchool(jsonObject.getString("school"));
        JSONArray studentsArray = jsonObject.getJSONArray("students");
        List<Student> studentsList = new ArrayList<>();
        for (int i = 0; i < studentsArray.length(); i++) {
            JSONObject studentObj = studentsArray.getJSONObject(i);
            Student student = std_repo.findByNmec(studentObj.getLong("nmec")).get();
            if (student != null)
                studentsList.add(student);
        }
        studentClass.setStudents(studentsList);

        JSONArray teachersArray = jsonObject.getJSONArray("teachers");
        List<Teacher> teachersList = new ArrayList<>();
        for (int i = 0; i < teachersArray.length(); i++) {
            JSONObject teacherObj = teachersArray.getJSONObject(i);
            Teacher teacher = teacher_repo.findByNmec(teacherObj.getLong("nmec")).get();
            if (teacher != null)
                teachersList.add(teacher);
        }
        studentClass.setTeachers(teachersList);

        JSONArray subjectsArray = jsonObject.getJSONArray("subjects");
        List<Subject> subjectsList = new ArrayList<>();

        for (int i = 0; i < subjectsArray.length(); i++) {
            JSONObject subjectObj = subjectsArray.getJSONObject(i);
            Subject subject = subject_repo.findById((long) subjectObj.getInt("id")).get();
            if (subject != null)
                subjectsList.add(subject);
        }
        studentClass.setSubjects(subjectsList);

        sclass_repo.save(studentClass);
    }

    public void AddTeacher(JSONObject jsonObject){
        Teacher teacher = new Teacher();
        teacher.setName(jsonObject.getString("name"));
        teacher.setEmail(jsonObject.getString("email"));
        teacher.setPassword(jsonObject.getString("password"));
        teacher.setNmec(jsonObject.getLong("nmec"));
        teacher.setSchool(jsonObject.getString("school"));
        JSONArray classesArray = jsonObject.getJSONArray("s_classes");

        List<S_class> classesList = new ArrayList<>();
        for (int i = 0; i < classesArray.length(); i++) {
            JSONObject classObj = classesArray.getJSONObject(i);
            S_class s_Class = sclass_repo.findById((long) classObj.getInt("id")).get();
            if (s_Class != null)
                classesList.add(s_Class);
        }
        JSONArray subjectsArray = jsonObject.getJSONArray("subjects");
        List<Subject> subjectsList = new ArrayList<>();
        for (int i = 0; i < subjectsArray.length(); i++) {
            JSONObject subjectObj = subjectsArray.getJSONObject(i);
            Subject subject = subject_repo.findById((long) subjectObj.getInt("id")).get();
            if (subject != null)
                subjectsList.add(subject);
        }
        teacher.setSubjects(subjectsList);
        teacher.setClasses(classesList);
        teacher_repo.save(teacher);
    }

    public void AddSubject(JSONObject jsonObject){
        Subject subject = new Subject();
        subject.setName(jsonObject.getString("name"));

        JSONArray teachersArray = jsonObject.getJSONArray("teachers");
        List<Teacher> teachersList = new ArrayList<>();
        for (int i = 0; i < teachersArray.length(); i++) {
            JSONObject teacherObj = teachersArray.getJSONObject(i);
            Teacher teacher = teacher_repo.findByNmec(teacherObj.getLong("nmec")).get();
            if (teacher != null)
                teachersList.add(teacher);
        }
        subject.setTeachers(teachersList);

        JSONArray classesArray = jsonObject.getJSONArray("classes");
        List<S_class> classesList = new ArrayList<>();
        for (int i = 0; i < classesArray.length(); i++) {
            JSONObject classObj = classesArray.getJSONObject(i);
            S_class s_Class = sclass_repo.findById((long) classObj.getInt("id")).get();
            if (s_Class != null)
                classesList.add(s_Class);
        }
        subject.setClasses(classesList);

        subject_repo.save(subject);
    }

    public void AddAssigment(JSONObject jsonObject){
        Teaching_Assignment assignment = new Teaching_Assignment();
        JSONObject ClassJson = jsonObject.getJSONObject("class");
        S_class class_ = sclass_repo.findById((long) ClassJson.getInt("id")).get();
        if (class_ != null)
            assignment.setSclass(class_);

        JSONObject subjectJson = jsonObject.getJSONObject("subject");
        Subject subject = subject_repo.findById((long) subjectJson.getInt("id")).get();
        if (subject != null)
            assignment.setSubject(subject);

        JSONObject teacherJson = jsonObject.getJSONObject("teacher");
        Teacher teacher = teacher_repo.findByNmec(teacherJson.getLong("nmec")).get();
        if (teacher != null)
            assignment.setTeacher(teacher);

        teaching_assignment_repo.save(assignment);
    }

    public void AddGrade(JSONObject jsonObject){
        Grade grade = new Grade();
        grade.setGrade(jsonObject.getInt("grade"));
        //grade.setWeight(jsonObject.getInt("weight"));

        JSONObject studentJson = jsonObject.getJSONObject("student");
        Student student = std_repo.findByNmec(studentJson.getLong("nmec")).get();
        if (student != null)
            grade.setStudent(student);

        JSONObject subjectJson = jsonObject.getJSONObject("subject");
        Subject subject = subject_repo.findById((long) subjectJson.getInt("id")).get();
        if (subject != null)
            grade.setSubject(subject);

        JSONObject teacherJson = jsonObject.getJSONObject("teacher");
        Teacher teacher = teacher_repo.findByNmec(teacherJson.getLong("nmec")).get();
        if (teacher != null)
            grade.setTeacher(teacher);

        grade_repo.save(grade);
    }
}