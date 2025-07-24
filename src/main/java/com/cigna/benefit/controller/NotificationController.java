package com.cigna.benefit.controller;

import com.cigna.benefit.model.Notification;
import com.cigna.benefit.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Notification> getNotificationById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return repository.save(notification);
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        return repository.findByReadFalse();
    }


    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable String id) {
        Optional<Notification> optional = repository.findById(id);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            notification.setRead(true);
            return repository.save(notification);
        }
        throw new RuntimeException("Notification not found with id " + id);
    }

//    @PutMapping("/{id}/read")
//    public void markAsReadAndDelete(@PathVariable String id) {
//        Optional<Notification> optional = repository.findById(id);
//        if (optional.isPresent()) {
//            repository.deleteById(id); // Auto delete after read
//        } else {
//            throw new RuntimeException("Notification not found with id " + id);
//        }
//    }


    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable String id) {
        repository.deleteById(id);
    }
}

