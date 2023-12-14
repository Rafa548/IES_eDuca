package g26.eDucaApp.Services;

import g26.eDucaApp.Model.Student;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import g26.eDucaApp.Services.kafka_messages.Producer;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class initScript {

    @Autowired
    private Producer producer;

    private boolean initialized = false;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        // Perform initialization when the application is ready
        String message = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "init");
        message = jsonObject.toString();
        System.out.println("Produced message: " + message);
        producer.sendMessage(message);
        initialized = true;
    }

    @Scheduled(fixedDelay = 7000) // Send every 15 seconds (adjust as needed)
    public void sendMessagePeriodically() {
        if (!initialized) {
            return;
        }
        try {
            // Creating a JSON message for periodic sending
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "periodic");
            String message = jsonObject.toString();

            // Sending the periodic message to Kafka
            System.out.println("Produced periodic message: " + message);
            producer.sendMessage(message);
        } catch (Exception e) {
            // Handle any exceptions or errors here
            e.printStackTrace();
        }
    }

}
