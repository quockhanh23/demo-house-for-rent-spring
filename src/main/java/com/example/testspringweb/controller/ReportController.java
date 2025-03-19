package com.example.testspringweb.controller;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.common.NotificationConstant;
import com.example.testspringweb.models.Report;
import com.example.testspringweb.repository.ReportRepository;
import com.example.testspringweb.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/reposts")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAllRepostByIdHouse")
    public ResponseEntity<Object> getAllRepostByIdHouse(@RequestParam Long idHouse) {
        try {
            return new ResponseEntity<>(reportRepository.countAllByIdHouse(idHouse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createReport")
    public ResponseEntity<Object> createReport(
            @RequestParam Long idHouse, @RequestParam Long idUserAction) {
        try {
            Report report = new Report();
            report.setIdUserReport(idUserAction);
            report.setIdHouse(idHouse);
            report.setCreatedAt(new Date());
            report.setStatus(CommonConstant.ACTIVE);
            reportRepository.save(report);
            notificationService.createNotification(idHouse, idUserAction, NotificationConstant.NEW_REPORT);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateStatusReport")
    public ResponseEntity<Object> updateStatusReport(@RequestParam Long idReport) {
        try {
            boolean result = true;
            Optional<Report> reportOptional = reportRepository.findById(idReport);
            if (reportOptional.isPresent()) {
                reportOptional.get().setStatus(CommonConstant.INACTIVE);
            } else {
                result = false;
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
