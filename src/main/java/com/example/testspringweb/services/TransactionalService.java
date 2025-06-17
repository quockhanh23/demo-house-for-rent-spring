package com.example.testspringweb.services;

import com.example.testspringweb.dto.TransactionalHistoryUser;
import com.example.testspringweb.models.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionalService {

    Transactional getDetailTransactional(Long transactionalId);

    Transactional createTransactional(Transactional transactional);

    Transactional updateTransactional(Long transactionalId, String status);

    Transactional checkIn(Long transactionalId, Long userId);

    Page<Transactional> getAllTransactionalPage(Pageable pageable);

    Page<Transactional> getAllTransactionalByHouseId(Long houseId, Pageable pageable);

    Page<TransactionalHistoryUser> getAllTransactionalByUser(Long idUser, Pageable pageable);

    BigDecimal totalMonthly(Long userId, String month);

    boolean cancelRental(Long transactionalId, Long userId);
}
