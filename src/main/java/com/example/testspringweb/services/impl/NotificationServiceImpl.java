package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.NotificationConstant;
import com.example.testspringweb.models.House;
import com.example.testspringweb.models.Notification;
import com.example.testspringweb.repository.NotificationRepository;
import com.example.testspringweb.services.HouseService;
import com.example.testspringweb.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private HouseService houseService;


    @Override
    public List<Notification> getAllByIdUserOrderByCreatedAtDesc(Long idUser) {
        List<Notification> notificationList = notificationRepository.getAllByIdUserOrderByCreatedAtDesc(idUser);
        if (CollectionUtils.isEmpty(notificationList)) {
            notificationList = new ArrayList<>();
        }
        return notificationList;
    }

    @Override
    public void createNotification(Long idHouse, Long idUserAction, String actionName) {
        House house = houseService.getDetailHouse(idHouse);
        Notification notification = new Notification();
        notification.setCreatedAt(new Date());
        notification.setIdUser(house.getIdUser());
        notification.setStatus(NotificationConstant.NOT_SEEN);
        notification.setContent(actionName);
        notificationRepository.save(notification);
    }

    @Override
    public boolean updateNotification(Long idNotification) {
        Optional<Notification> notificationOptional = notificationRepository.findById(idNotification);
        if (notificationOptional.isEmpty()) {
            return false;
        }
        notificationOptional.get().setUpdatedAt(new Date());
        notificationOptional.get().setStatus(NotificationConstant.SEEN);
        notificationRepository.save(notificationOptional.get());
        return true;
    }

    @Override
    public void updateAllNotification(Long idUser, String status) {
        notificationRepository.updateAllNotificationByIdUser(idUser, status);
    }
}
