package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.ActionNotification;
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
        String content = "";
        switch (actionName) {
            case ActionNotification.CREATE_COMMENT -> content = "Ai đó đã bình luận căn nhà của bạn";
            case ActionNotification.CREATE_TRANSACTIONAL -> content = "Ai đó đã đặt thuê căn nhà của bạn";
            case ActionNotification.CANCEL_TRANSACTIONAL -> content = "Ai đó đã hủy thuê căn nhà của bạn";
            case ActionNotification.REPORT -> content = "Ai đó đã báo cáo căn nhà của bạn";
            case ActionNotification.REVIEW -> content = "Ai đó đã đánh giá căn nhà của bạn";
        }
        House house = houseService.getDetailHouse(idHouse);
        Notification notification = new Notification();
        notification.setCreatedAt(new Date());
        notification.setIdUser(house.getIdUser());
        notification.setStatus(NotificationConstant.NOT_SEEN);
        notification.setContent(content);
        notification.setAction(actionName);
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
}
