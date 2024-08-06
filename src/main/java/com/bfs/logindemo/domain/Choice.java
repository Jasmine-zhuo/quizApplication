package com.bfs.logindemo.domain;

import lombok.*;

import jakarta.persistence.*;
//import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="Choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private int choiceId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}