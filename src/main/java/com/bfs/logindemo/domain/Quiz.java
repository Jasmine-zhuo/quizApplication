package com.bfs.logindemo.domain;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Quiz {
    private int quizId;
    private int userId;
    private int categoryId;
    private String name;
    private Timestamp timeStart;
    private Timestamp timeEnd;
}
