package com.bfs.logindemo.domain;

import lombok.*;
//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "quizzes")
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Quiz> quizzes;

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
