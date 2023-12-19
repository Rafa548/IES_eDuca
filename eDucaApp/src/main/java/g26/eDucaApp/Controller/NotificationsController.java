package g26.eDucaApp.Controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import g26.eDucaApp.Model.Notification;
import g26.eDucaApp.Model.NotificationType;

import g26.eDucaApp.Repository.NotificationRepository;
import g26.eDucaApp.Services.EducaServices;
import g26.eDucaApp.Services.notifications.notificationsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
public class NotificationsController {

    @Autowired
    private EducaServices EducaServices;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/class/{classname}") //not used
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

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1000") int size) {
        try {
            List<Notification> notifications = new ArrayList<Notification>();
            notifications = notificationRepository.findAll(PageRequest.of(page, size)).getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("notifications", notifications);
            
            return new ResponseEntity<>(notifications, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @GetMapping("/notification/{user}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable("user") String user, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10000") int size) {
        try {
            List<Notification> notifications = new ArrayList<Notification>();
            notifications = notificationRepository.findByReceiver(user, PageRequest.of(page, size));

            Map<String, Object> response = new HashMap<>();
            response.put("notifications", notifications);
            
            return new ResponseEntity<>(notifications, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/notification")
    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, String> body) {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")) {
                try {
                    String message = body.get("message");
                    String receiver = body.get("receiver");
                    String type = body.get("type");
                    Notification notification = new Notification();

                    if (message != null) {
                        notification.setMessage(message);
                    }
                    if (receiver != null) {
                        notification.setReceiver(receiver);
                    }

                    if (type != null) {
                        notification.setType(NotificationType.valueOf(type));
                    }
                    System.out.println(notification);
                    Notification _notification = EducaServices.createNotification(notification);
                    //System.out.println(_notification);
                    return new ResponseEntity<>(_notification, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
