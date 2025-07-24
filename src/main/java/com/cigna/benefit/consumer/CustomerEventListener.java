package com.cigna.benefit.consumer;

import com.cigna.benefit.model.CustomerEvent;
import com.cigna.benefit.model.Notification;
import com.cigna.benefit.repository.NotificationRepository;
import com.cigna.benefit.websocket.WebSocketSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventListener {

    @Autowired
    private WebSocketSessionManager webSocketHandler;

    @Autowired
    private NotificationRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "customer-event-topic", groupId = "health-bene-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(CustomerEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);

            // Store notification with isRead = false
            Notification notification = new Notification();
            notification.setCustomerName((String) event.getCustomerNotification().get("customerName"));
            notification.setPlanId((String) event.getCustomerNotification().get("planId"));
            notification.setMessage(json); // original message
            notification.setRead(false);

            repository.save(notification);
            System.out.println(json);

            // Send to WebSocket
            webSocketHandler.sendMessageToAll(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

