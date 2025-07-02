package com.example.testspringweb.services;

import com.example.testspringweb.models.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getAllByIdUserOrderByCreatedAtDesc(Long idUser);

    void createNotification(Long idHouse, Long idUserAction, String actionName, Long idUserSendTo);

    void updateNotification(Long idNotification);

    void updateAllNotification(Long idUser, String status);
}
