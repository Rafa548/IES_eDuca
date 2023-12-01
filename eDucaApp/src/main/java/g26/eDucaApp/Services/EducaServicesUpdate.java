package g26.eDucaApp.Services;

import g26.eDucaApp.Model.*;
import g26.eDucaApp.Repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducaServicesUpdate {

    @Autowired
    private StudentRepository std_repo;

    @Autowired
    private S_ClassRepository sclass_repo;

    @Autowired
    private TeacherRepository teacher_repo;

    @Autowired
    private SubjectRepository subject_repo;

    @Autowired
    private Teaching_AssignmentRepository t_a_repo;

    @Autowired
    private GradeRepository grade_repo;


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

        t_a_repo.save(assignment);
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
