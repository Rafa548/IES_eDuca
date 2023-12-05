package g26.eDucaApp.Services.kafka_messages;

import g26.eDucaApp.Services.EducaServices;
import g26.eDucaApp.Controller.ProgressController;
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

    @Autowired
    private ProgressController progressController;

    @KafkaListener(topics = "eDuca_dataGen")
    public void consume(String message) throws IOException {
        if (!isValid(message)) {
            System.out.println("Invalid message: " + message);
            return;
        }
        JSONObject jsonObject = new JSONObject(message);
        //System.out.println("Consumed message: " + message);
        String type = jsonObject.getString("type");
        if (type.equals("task")){
            System.out.println("Consumed message: " + message);
            String t_type = jsonObject.getString("task_type");
            int currentTotalExpected = progressController.getTotalExpected(t_type);
            int newTotalExpected = jsonObject.getInt("total_expected");
            int updatedTotalExpected = currentTotalExpected + newTotalExpected;
            if (updatedTotalExpected != currentTotalExpected) {
                progressController.setTotalExpectedCount(t_type, updatedTotalExpected);
            }
        }
        else if (type.equals("student")) {
            educaServices.AddStudent(jsonObject);
            progressController.incrementProgress("students");
        }
        else if (type.equals("class")) {
            educaServices.AddClass(jsonObject);
            progressController.incrementProgress("classes");
        }
        else if (type.equals("teacher")) {
            educaServices.AddTeacher(jsonObject);
            progressController.incrementProgress("teachers");
        }
        else if (type.equals("subject")) {
            educaServices.AddSubject(jsonObject);
            progressController.incrementProgress("subjects");
        }
        else if (type.equals("assigment")) {
            educaServices.AddAssigment(jsonObject);
            progressController.incrementProgress("assigments");
        }
        else if (type.equals("grade")) {
            educaServices.AddGrade(jsonObject);
            progressController.incrementProgress("grades");
        }
    }
    
}
