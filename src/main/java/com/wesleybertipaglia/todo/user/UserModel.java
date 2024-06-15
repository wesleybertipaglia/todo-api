package com.wesleybertipaglia.todo.user;

import lombok.Data;

@Data
public class UserModel {
    public String name;
    public String username;
    public String password;

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel() {
    }
}
