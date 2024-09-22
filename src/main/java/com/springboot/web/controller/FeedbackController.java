package com.springboot.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.model.Feedback;
import com.springboot.web.service.FeedbackService;


@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

     @Autowired
    private FeedbackService feedbackService;

    @CrossOrigin(origins = "https://erp-frontend-phi-nine.vercel.app/") // Adjust the port if needed
    @PostMapping("/submit")
    public ResponseEntity<Feedback> feedbackData(@RequestBody Feedback feedback) {
        System.out.println("Received feedback data: " + feedback);
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }



}
