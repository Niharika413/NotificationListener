package com.consumer.benefit.consumer;

import com.consumer.benefit.model.CustomerEvent;
import com.consumer.benefit.websocket.WebSocketSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventListener {

    @Autowired
    private WebSocketSessionManager webSocketHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "benefit-events", groupId = "health-bene-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(CustomerEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            System.out.println(json);
            webSocketHandler.sendMessageToAll(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

