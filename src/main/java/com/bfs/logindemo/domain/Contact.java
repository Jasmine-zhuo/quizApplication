package com.bfs.logindemo.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private int contactId;
    private String subject;
    private String message;
    private String email;
    private Timestamp time;

    public Contact(String subject, String message, String email) {
        this.subject = subject;
        this.message = message;
        this.email = email;
        this.time = new Timestamp(System.currentTimeMillis());
    }
}
