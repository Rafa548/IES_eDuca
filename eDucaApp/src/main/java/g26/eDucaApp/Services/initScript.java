package g26.eDucaApp.Services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import g26.eDucaApp.Services.messaging.Producer;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class initScript {
    
        @Autowired
        private Producer producer;
    
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

        }


    
}
