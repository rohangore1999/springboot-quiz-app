package com.example.quizapp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data // to create getter and setter methods
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to start with next id from existing.

    private Integer id;
    private String title;

    @ManyToMany
    // many to many -> A quiz contains multiple questions, and a question can be part of multiple quizzes. This forms a many-to-many relationship because:
    private List<Question> questions;
}
