package com.example.testspringweb.controller;

import com.example.testspringweb.models.Notification;
import com.example.testspringweb.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAllNotificationByIdUser")
    public ResponseEntity<Object> getAllNotificationByIdUser(@RequestParam Long idUser) {
            List<Notification> notificationList = notificationService.getAllByIdUserOrderByCreatedAtDesc(idUser);
            return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PostMapping("/createNotification")
    public ResponseEntity<Object> createNotification(
            @RequestParam Long idHouse, @RequestParam Long idUserAction, @RequestParam String actionName) {
            notificationService.createNotification(idHouse, idUserAction, actionName);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updateNotification")
    public ResponseEntity<Object> updateNotification(@RequestParam Long idNotification) {
        notificationService.updateNotification(idNotification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updateAllNotification")
    public ResponseEntity<Object> updateAllNotification(@RequestParam Long idUser, @RequestParam String status) {
        notificationService.updateAllNotification(idUser, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
