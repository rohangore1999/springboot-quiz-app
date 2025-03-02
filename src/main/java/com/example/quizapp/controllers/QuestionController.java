package com.example.quizapp.controllers;

import com.example.quizapp.services.QuestionService;
import com.example.quizapp.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/all-questions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
