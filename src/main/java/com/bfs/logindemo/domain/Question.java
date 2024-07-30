package com.bfs.logindemo.domain;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    private int questionId;
    private int categoryId;
    private String description;
    private boolean isActive;
    private List<Choice> choices;

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
