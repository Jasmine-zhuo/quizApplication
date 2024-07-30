package com.bfs.logindemo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int userId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private boolean isActive;
    private boolean isAdmin;
}
