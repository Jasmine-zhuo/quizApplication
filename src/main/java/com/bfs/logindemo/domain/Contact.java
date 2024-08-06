package com.bfs.logindemo.domain;

import lombok.*;
//import javax.persistence.*;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private int contactId;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp time;

    public Contact(String subject, String message, String email) {
        this.subject = subject;
        this.message = message;
        this.email = email;
        this.time = new Timestamp(System.currentTimeMillis());
    }
}
