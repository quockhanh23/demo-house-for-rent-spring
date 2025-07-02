package com.example.testspringweb.controller;

import com.example.testspringweb.models.Comment;
import com.example.testspringweb.services.CommentService;
import com.example.testspringweb.services.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommonService commonService = new CommonService();

    @GetMapping("/getAllCommentByHouseId")
    public ResponseEntity<Object> getAllCommentByHouseId(@RequestParam Long houseId,
                                                         @RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> housePage = commentService.getAllCommentByHouseId(houseId, pageable);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @PostMapping("/createComment")
    public ResponseEntity<Object> createComment(@RequestBody Comment comment, BindingResult errors) {
        Map<String, String> validate = commonService.validateInput(errors);
        if (Objects.nonNull(validate)) {
            return new ResponseEntity<>(validate, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(commentService.createComment(comment), HttpStatus.CREATED);
    }

    @PostMapping("/updateComment")
    public ResponseEntity<Object> updateComment(@RequestParam Long idComment, @RequestParam Long idUser) {
        boolean checkComment = commentService.deleteComment(idComment, idUser);
        return new ResponseEntity<>(checkComment, HttpStatus.OK);
    }
}
