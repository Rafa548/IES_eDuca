package g26.eDucaApp.Services.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import g26.eDucaApp.Model.Notification;
import g26.eDucaApp.Model.NotificationType;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Services.EducaServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class notificationsService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public String toJson(Notification notification) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(notification);
    }
    
    public void sendNotification(Notification notification) {
        try {
            String json = toJson(notification);
            simpMessagingTemplate.convertAndSendToUser(notification.getReceiver(), "queue/notifications", json);
            //simpMessagingTemplate.convertAndSendToUser("admin@gmail.com", "queue/notifications", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    
}
