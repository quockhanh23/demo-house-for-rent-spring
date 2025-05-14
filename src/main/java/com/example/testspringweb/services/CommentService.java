package com.example.testspringweb.services;

import com.example.testspringweb.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<Comment> getAllCommentByHouseId(Long idHouse, Pageable pageable);

    Comment createComment(Comment comment);

    boolean deleteComment(Long idComment, Long idUser);
}
