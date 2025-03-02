package com.example.quizapp.services;

import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.QuestionWrapper;
import com.example.quizapp.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDAO quizDao;

    @Autowired
    QuestionDAO questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // get random question from QuestionDAO (table)
        List<Question> question =  questionDao.findRandomQuestionsByCategory(category, numQ);

        // create quiz obj and save in Quiz table
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(question);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        // get id
        Optional<Quiz> quiz = quizDao.findById(id); // <Optional> if not data for given id, so optional field

        // the quiz object will contain its id, title and questions.
        // But we need to map the question to questionWrapper(to get only options not answer).
        List<Question> questionsFromDB = quiz.get().getQuestions(); // using get() as quiz is the optional field; getQuestions() -> getter method of Quiz

        // create list to manually convert the question -> questionWrapper
        List<QuestionWrapper> questionsToUser = new ArrayList<>();
        for(Question question: questionsFromDB){
            QuestionWrapper questionObj = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(),question.getOption3(), question.getOption4());

            questionsToUser.add(questionObj);
        }

        return new ResponseEntity<>(questionsToUser, HttpStatus.OK);
    }
}
