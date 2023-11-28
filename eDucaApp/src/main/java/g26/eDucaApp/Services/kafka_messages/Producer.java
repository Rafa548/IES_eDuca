package g26.eDucaApp.Services.kafka_messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final String TOPIC = "eDucaApp";

    /*
    Supressed Warning as advised in:
    https://stackoverflow.com/questions/55280173/the-correct-way-for-creation-of-kafkatemplate-in-spring-boot
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){

        this.kafkaTemplate.send(TOPIC, message);
        this.kafkaTemplate.flush();
    }
    
}
