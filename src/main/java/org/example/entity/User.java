package org.example.entity;

public class User {

    private Long id;

    private String userName;

    public User() {}

    public User(Long id, String name) {
        this.userName = name;
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    @Override
    public String toString() {
        return "Users{id=" + id + ", username='" + userName + "}";
    }
}


