package com.bfs.logindemo.domain;

import lombok.*;
//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "category")
@Entity
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Choice> choices;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizQuestion> quizQuestions;

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public boolean getActive(){
        return isActive;
    }
    public boolean isActive(){
        return isActive;
    }
}
