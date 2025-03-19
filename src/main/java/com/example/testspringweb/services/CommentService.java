package com.example.testspringweb.services;

import com.example.testspringweb.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<Comment> getAllCommentByHouseId(Long houseId, Pageable pageable);

    boolean createComment(Long idUser, Long idHouse);

    boolean deleteComment(Long idComment, Long idUser);
}
