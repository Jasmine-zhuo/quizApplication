package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWithCategoryDTO {
    private Question question;
    private String categoryName;
}
