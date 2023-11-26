package g26.eDucaApp.Services.kafka_messages;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Repository.StudentRepository;
import g26.eDucaApp.Repository.TeacherRepository;
import g26.eDucaApp.Services.EducaServices;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer {

    public boolean isValid(String json) {
    try {
        new JSONObject(json);
    } catch (JSONException e) {
        return false;
    }
    return true;
    }

    @Autowired
    private EducaServices educaServices;

    @KafkaListener(topics = "eDuca_dataGen")
    public void consume(String message) throws IOException {
        if (!isValid(message)) {
            System.out.println("Invalid message: " + message);
            return;
        }
        JSONObject jsonObject = new JSONObject(message);
        System.out.println("Consumed message: " + message);
        String type = jsonObject.getString("type");
        if (type.equals("student")) {
            Student student = new Student();
            student.setName(jsonObject.getString("name"));
            student.setEmail(jsonObject.getString("email"));
            student.setPassword(jsonObject.getString("password"));
            student.setNmec(jsonObject.getLong("nmec"));
            student.setSchool(jsonObject.getString("school"));

            JSONObject studentClassJson = jsonObject.getJSONObject("studentclass");
            S_class studentClass = educaServices.getS_classById((long) studentClassJson.getInt("id"));

            if (studentClass != null) {
                student.setStudentclass(studentClass);
            }

            educaServices.createStudent(student);
        }
        else if (type.equals("class")) {
            // Handle class message
            S_class studentClass = new S_class();
            studentClass.setClassname(jsonObject.getString("classname"));
            studentClass.setSchool(jsonObject.getString("school"));
            JSONArray studentsArray = jsonObject.getJSONArray("students");
            List<Student> studentsList = new ArrayList<>();
            for (int i = 0; i < studentsArray.length(); i++) {
                JSONObject studentObj = studentsArray.getJSONObject(i);
                Student student = educaServices.getStudentByNmec(studentObj.getLong("nmec"));
                if (student != null)
                    studentsList.add(student);
            }
            studentClass.setStudents(studentsList);

            JSONArray teachersArray = jsonObject.getJSONArray("teachers");
            List<Teacher> teachersList = new ArrayList<>();
            for (int i = 0; i < teachersArray.length(); i++) {
                JSONObject teacherObj = teachersArray.getJSONObject(i);
                Teacher teacher = educaServices.getTeacherByN_mec(teacherObj.getLong("nmec"));
                if (teacher != null)
                    teachersList.add(teacher);
            }
            studentClass.setTeachers(teachersList);

            JSONArray subjectsArray = jsonObject.getJSONArray("subjects");
            List<Subject> subjectsList = new ArrayList<>();

            for (int i = 0; i < subjectsArray.length(); i++) {
                JSONObject subjectObj = subjectsArray.getJSONObject(i);
                Subject subject = educaServices.getSubjectById((long) subjectObj.getInt("id"));
                if (subject != null)
                    subjectsList.add(subject);
            }
            studentClass.setSubjects(subjectsList);

            educaServices.createS_class(studentClass);
        }
        else if (type.equals("teacher")) {
            Teacher teacher = new Teacher();
            teacher.setName(jsonObject.getString("name"));
            teacher.setEmail(jsonObject.getString("email"));
            teacher.setPassword(jsonObject.getString("password"));
            teacher.setNmec(jsonObject.getLong("nmec"));
            teacher.setSchool(jsonObject.getString("school"));
            JSONArray classesArray = jsonObject.getJSONArray("s_classes");
            System.out.println(classesArray);
            List<S_class> classesList = new ArrayList<>();
            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject classObj = classesArray.getJSONObject(i);
                S_class s_Class = educaServices.getS_classById((long) classObj.getInt("id"));
                if (s_Class != null)
                    classesList.add(s_Class);
            }
            JSONArray subjectsArray = jsonObject.getJSONArray("subjects");
            List<Subject> subjectsList = new ArrayList<>();
            for (int i = 0; i < subjectsArray.length(); i++) {
                JSONObject subjectObj = subjectsArray.getJSONObject(i);
                Subject subject = educaServices.getSubjectById((long) subjectObj.getInt("id"));
                if (subject != null)
                    subjectsList.add(subject);
            }
            teacher.setSubjects(subjectsList);
            teacher.setClasses(classesList);
            educaServices.createTeacher(teacher);
        }
        else if (type.equals("subject")) {
            Subject subject = new Subject();
            subject.setName(jsonObject.getString("name"));

            JSONArray teachersArray = jsonObject.getJSONArray("teachers");
            List<Teacher> teachersList = new ArrayList<>();
            for (int i = 0; i < teachersArray.length(); i++) {
                JSONObject teacherObj = teachersArray.getJSONObject(i);
                Teacher teacher = educaServices.getTeacherByN_mec(teacherObj.getLong("nmec"));
                if (teacher != null)
                    teachersList.add(teacher);
            }
            subject.setTeachers(teachersList);

            JSONArray classesArray = jsonObject.getJSONArray("classes");
            List<S_class> classesList = new ArrayList<>();
            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject classObj = classesArray.getJSONObject(i);
                S_class s_Class = educaServices.getS_classById((long) classObj.getInt("id"));
                if (s_Class != null)
                    classesList.add(s_Class);
            }
            subject.setClasses(classesList);

            educaServices.createSubject(subject);
        }
    }
    
}
