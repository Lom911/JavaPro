package org.example;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProducerApp {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public ProducerApp(KafkaTemplate<String, String> kafkaTemplate,
                       @Value("${app.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @PostConstruct
    public void send() {

        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString();
            kafkaTemplate.send(topic, uuid).whenComplete((md, ex) -> {
                if (ex != null) {
                    log.error("Ошибка при отправке сообщения {}", uuid, ex);
                } else {
                    log.info("Отправлено сообщение {}", uuid);
                }
            });
        }
    }
}