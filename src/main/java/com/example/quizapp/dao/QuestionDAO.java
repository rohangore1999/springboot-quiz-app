package com.example.quizapp.dao;

import com.example.quizapp.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {
    // As "findByCategory" is a custom method, we just have to mention its return type and args.
    List<Question> findByCategory(String category);
}
