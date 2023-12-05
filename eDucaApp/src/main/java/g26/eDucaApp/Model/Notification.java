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
import java.util.Date;

@Document(collection = "notifications")
@Data
public class Notification implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @CreatedDate
    private Date createdDate;

    public Notification() {
    }

    public Notification(String message, NotificationType type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", createdDate=" + createdDate +
                '}';
    }
    
}
