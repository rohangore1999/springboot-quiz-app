package com.example.quizapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // to get getter and setter
@Entity
public class Question {
    // To make id as primary key; id should be autogenerated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to start with next id from existing.

    private int id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultyLevel;
    private String category;
}
