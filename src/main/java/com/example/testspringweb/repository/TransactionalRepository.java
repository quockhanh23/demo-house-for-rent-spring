package com.example.testspringweb.repository;

import com.example.testspringweb.models.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionalRepository extends JpaRepository<Transactional, Long> {

    @Query(value = "select * from transactional order by created_at desc", nativeQuery = true)
    Page<Transactional> getAllTransactionalPage(Pageable pageable);

    @Query(value = "select * from transactional where id_house = :houseId order by created_at desc", nativeQuery = true)
    Page<Transactional> getAllTransactionalByHouseId(Long houseId, Pageable pageable);

    @Query(value = "select * from transactional where id_user_guest = :idUser", nativeQuery = true)
    List<Transactional> getAllTransactionalByUser(Long idUser);

    @Query(value = "select * from transactional where (id_house = :houseId) and (status = 'PROCESSING' or status = 'CONFIRM')", nativeQuery = true)
    List<Transactional> getAllTransactionalByHouseId(Long houseId);

    @Query(value = "select * from transactional where MONTH(updated_at) = :month and id_user_host = :userId and status = :status", nativeQuery = true)
    List<Transactional> getTotalMonthlyByUserId(@Param("month") String month,
                                                @Param("userId") Long userId,
                                                @Param("status") String status);
}
