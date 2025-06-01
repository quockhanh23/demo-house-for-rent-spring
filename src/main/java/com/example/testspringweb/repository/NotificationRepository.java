package com.example.testspringweb.repository;

import com.example.testspringweb.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> getAllByIdUserOrderByCreatedAtDesc(Long idUser);

    @Modifying
    @Transactional
    @Query(value = "update notification set status = :status, updated_at = (current_timestamp) where id_user = :idUser", nativeQuery = true)
    void updateAllNotificationByIdUser(@Param("idUser") Long idUser, @Param("status") String status);
}
