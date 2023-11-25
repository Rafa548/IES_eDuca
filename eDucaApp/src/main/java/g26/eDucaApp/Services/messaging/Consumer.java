package g26.eDucaApp.Services.messaging;

import g26.eDucaApp.Model.Student;
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

import java.io.IOException;

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
            /* JSONArray jsonArray = jsonObject.getJSONArray("s_classes");
            for (int i = 0; i < jsonArray.length(); i++) {
                student.getS_classes().add(jsonArray.getString(i));
            } */
            educaServices.createStudent(student);
        } /* else if (type.equals("teacher")) {
            Teacher teacher = new Teacher();
            teacher.setName(jsonObject.getString("name"));
            teacher.setEmail(jsonObject.getString("email"));
            teacher.setPassword(jsonObject.getString("password"));
            teacher.setNmec(jsonObject.getLong("nmec"));
            teacher.setSchool(jsonObject.getString("school"));
            JSONArray jsonArray = jsonObject.getJSONArray("s_classes");
            for (int i = 0; i < jsonArray.length(); i++) {
                teacher.getS_classes().add(jsonArray.getString(i));
            }
            educaServices.createTeacher(teacher);
        } */
    }
    
}
