package com.bfs.logindemo.dao;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class QuizWithDetailsDTO {
    private int quizId;
    private int userId;
    private int categoryId;
    private String name;
    private Timestamp timeStart;
    private Timestamp timeEnd;
    private String firstname;
    private String lastname;
    private String categoryName;
    private int numQuestions;
    private int score;
}

