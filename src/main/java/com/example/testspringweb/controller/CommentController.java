package com.example.testspringweb.controller;

import com.example.testspringweb.models.Comment;
import com.example.testspringweb.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllCommentByHouseId")
    public ResponseEntity<Object> getAllCommentByHouseId(@RequestParam Long houseId,
                                                         @RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> housePage = commentService.getAllCommentByHouseId(houseId, pageable);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @PostMapping("/createComment")
    public ResponseEntity<Object> createComment(
            @RequestParam Long idUser, @RequestParam Long isHouse) {
        try {
            boolean checkComment = commentService.createComment(idUser, isHouse);
            return new ResponseEntity<>(checkComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateComment")
    public ResponseEntity<Object> updateComment(@RequestParam Long idComment, @RequestParam Long idUser) {
        try {
            boolean checkComment = commentService.deleteComment(idComment, idUser);
            return new ResponseEntity<>(checkComment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
