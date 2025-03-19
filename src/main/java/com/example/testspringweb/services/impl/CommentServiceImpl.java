package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.models.Comment;
import com.example.testspringweb.repository.CommentRepository;
import com.example.testspringweb.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> getAllCommentByHouseId(Long houseId, Pageable pageable) {
        return commentRepository.getAllCommentByHouseId(houseId, pageable);
    }

    @Override
    public boolean createComment(Long idUser, Long idHouse) {
        Comment comment = new Comment();
        comment.setCreatedAt(new Date());
        comment.setIdUser(idUser);
        comment.setIdHouse(idHouse);
        comment.setStatus(CommonConstant.ACTIVE);
        commentRepository.save(comment);
        return true;
    }

    @Override
    public boolean deleteComment(Long idComment, Long idUser) {
        Optional<Comment> optionalComment = commentRepository.findById(idComment);
        if (optionalComment.isEmpty()) {
            return false;
        }
        optionalComment.get().setStatus(CommonConstant.ACTIVE);
        optionalComment.get().setUpdatedAt(new Date());
        commentRepository.save(optionalComment.get());
        return true;
    }
}
