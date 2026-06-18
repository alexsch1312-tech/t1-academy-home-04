package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_username", columnNames = "username")
})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String userName;

    public Users() {}

    public Users(String name) {
        this.userName = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String username) { this.userName = username; }

    @Override
    public String toString() {
        return "Users{id=" + id + ", username='" + userName + "}";
    }
}


