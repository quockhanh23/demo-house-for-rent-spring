package com.example.testspringweb.repository;

import com.example.testspringweb.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment where id_house = :houseId", nativeQuery = true)
    Page<Comment> getAllCommentByHouseId(Long houseId, Pageable pageable);
}
