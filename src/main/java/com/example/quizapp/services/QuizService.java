package com.example.quizapp.services;

import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    QuizDAO quizDao;

    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {

    }
}
