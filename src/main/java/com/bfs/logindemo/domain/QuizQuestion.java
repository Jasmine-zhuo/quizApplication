package com.bfs.logindemo.domain;

import lombok.*;
//import javax.persistence.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quiz", "question", "userChoice"})
@Entity
@Table(name = "QuizQuestion")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qq_id")
    private int qqId;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_choice_id")
    private Choice userChoice;
    //private int userChoiceId;
}
