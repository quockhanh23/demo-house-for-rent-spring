package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.models.Comment;
import com.example.testspringweb.repository.CommentRepository;
import com.example.testspringweb.services.CommentService;
import com.example.testspringweb.services.UserService;
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

    @Autowired
    private UserService userService;

    @Override
    public Page<Comment> getAllCommentByHouseId(Long idHouse, Pageable pageable) {
        return commentRepository.getAllCommentByHouseId(idHouse, pageable);
    }

    @Override
    public Comment createComment(Comment commentRequest) {
        UserDTOResponse user = userService.getDetailUser(commentRequest.getIdUser());
        Comment comment = new Comment();
        comment.setCreatedAt(new Date());
        comment.setIdUser(commentRequest.getIdUser());
        comment.setIdHouse(commentRequest.getIdHouse());
        comment.setStatus(CommonConstant.ACTIVE);
        comment.setContent(commentRequest.getContent());
        comment.setUsername(user.getUsername());
        return commentRepository.save(comment);
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
