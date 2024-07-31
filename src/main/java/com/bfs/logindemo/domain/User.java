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

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public boolean isActive() {
        return isActive;
    }

//    public boolean getActive() {
//        return isActive;
//    }
//    public boolean getIsActive() {
//        return isActive;
//    }
//    public void setActive(boolean isActive) {
//        this.isActive = isActive;
//    }

}
