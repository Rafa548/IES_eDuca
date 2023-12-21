package g26.eDucaApp.Model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
public class Notification implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String message;

    private String receiver;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String createdDate;

    public Notification() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Notification(String message, NotificationType type, String receiver) {
        this.message = message;
        this.type = type;
        this.receiver = receiver;
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", createdDate=" + createdDate +
                ", receiver='" + receiver + '\'' +
                '}';
    }
    
}
