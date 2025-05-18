package com.example.testspringweb.controller;

import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.models.Report;
import com.example.testspringweb.repository.ReportRepository;
import com.example.testspringweb.services.CommentService;
import com.example.testspringweb.services.NotificationService;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/getAllUser")
    public ResponseEntity<Object> getAllUser(@RequestParam Long idAdmin) {
        List<UserDTOResponse> userDTOResponseList = userService.getAllUser(idAdmin);
        return new ResponseEntity<>(userDTOResponseList, HttpStatus.OK);
    }

    @GetMapping("/getAllRepost")
    public ResponseEntity<Object> getAllRepost(@RequestParam Long idAdmin) {
        userService.checkAdmin(idAdmin);
        List<Report> reportList = reportRepository.findAll();
        if (CollectionUtils.isEmpty(reportList)) reportList = new ArrayList<>();
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    @PutMapping("/actionUser")
    public ResponseEntity<Object> activeUser(@RequestParam Long idAdmin,
                                             @RequestParam String action,
                                             @RequestParam Long idUser) {
        userService.actionUser(idAdmin, action, idUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
