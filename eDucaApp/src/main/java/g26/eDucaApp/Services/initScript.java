package g26.eDucaApp.Services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

        @PostConstruct
        public void init() {
            // wait 10 seconds for kafka to start
            /* try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */

            String message = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "init");
            message = jsonObject.toString();
            System.out.println("Produced message: " + message);
            producer.sendMessage(message);
            initialized = true;

        }

        @Scheduled(fixedDelay = 30000) // Send every 5 seconds (adjust as needed)
        public void sendMessagePeriodically() {
            if (!initialized) {
                // Wait for initialization before starting periodic messages
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
