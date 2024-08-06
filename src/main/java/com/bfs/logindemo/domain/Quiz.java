package com.bfs.logindemo.domain;

import lombok.*;
//import javax.persistence.*;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "quizQuestions"})
@Entity
@Table(name = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int quizId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "time_start", nullable = false)
    private Timestamp timeStart;
    @Column(name = "time_end")
    private Timestamp timeEnd;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizQuestion> quizQuestions;
}
