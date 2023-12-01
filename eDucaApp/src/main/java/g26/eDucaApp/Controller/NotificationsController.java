package g26.eDucaApp.Controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import g26.eDucaApp.Model.Notification;
import g26.eDucaApp.Model.NotificationType;

import g26.eDucaApp.Repository.NotificationRepository;
import g26.eDucaApp.Services.notifications.notificationsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationsController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/class/{classname}")
    public ResponseEntity<List<Notification>> getNotificationsByClassname(@PathVariable("classname") String classname, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            List<Notification> notifications = new ArrayList<Notification>();
            notifications = notificationRepository.findByType(NotificationType.GRADE, PageRequest.of(page, size));

            Map<String, Object> response = new HashMap<>();
            response.put("notifications", notifications);
            
            return new ResponseEntity<>(notifications, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
