package com.cigna.benefit.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
@Data
public class Notification {

    @Id
    private String id;

    private String customerName;
    private String planId;

    private String message;

    private boolean isRead = false;
}
