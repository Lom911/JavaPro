package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerApp {

    @KafkaListener(topics = "${app.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ConsumerRecord<String, String> r) throws InterruptedException {
        Thread.sleep(1000);
        log.info("Получено сообщение: key={} value={} topic={} partition={} offset={}",
                r.key(), r.value(), r.topic(), r.partition(), r.offset());
    }
}
