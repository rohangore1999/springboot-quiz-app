package com.example.quizapp.services;

import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.QuizDAO;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.QuestionWrapper;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.models.ResponseObj.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ResponseEntity<Integer> submitQuiz(Integer id, List<ResponseObj> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        // Map questions and their correct answers for quick lookup
        Map<String, String> correctAnswersMap = new HashMap<>();
        for (Question question : questions) {
            correctAnswersMap.put(String.valueOf(question.getId()), question.getRightAnswer());  // Assuming questions have unique IDs
        }

        int right = 0;

        // Iterate through each response and check if it matches the correct answer
        for (ResponseObj responseObj : responses) {
            String questionId = String.valueOf(responseObj.getId());  // Assuming ResponseObj has a questionId field
            String responseAnswer = responseObj.getResponse();

            // Check if the response matches the correct answer for the corresponding question
            if (correctAnswersMap.containsKey(questionId) && correctAnswersMap.get(questionId).equals(responseAnswer)) {
                right++;
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
