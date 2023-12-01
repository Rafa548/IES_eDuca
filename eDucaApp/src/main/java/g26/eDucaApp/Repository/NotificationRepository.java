package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Notification;
import g26.eDucaApp.Model.NotificationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String>{
    
    List<Notification> findByType(NotificationType type, Pageable pageable);
    
    List<Notification> findByCreatedDate(Date createdDate, Pageable pageable);
    
    List<Notification> findByTypeAndCreatedDate(NotificationType type, Date createdDate, Pageable pageable);
    
    List<Notification> findByTypeAndCreatedDateGreaterThan(NotificationType type, Date createdDate, Pageable pageable);
    
    List<Notification> findByTypeAndCreatedDateLessThan(NotificationType type, Date createdDate, Pageable pageable);
    
    List<Notification> findByTypeAndCreatedDateBetween(NotificationType type, Date createdDate1, Date createdDate2, Pageable pageable);
}
