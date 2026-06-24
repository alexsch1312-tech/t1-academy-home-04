package org.example.service;

import org.example.dao.UsersDAO;
import org.example.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    private final UsersDAO usersDAO;

    public UserService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public void createUser(String username) {
        usersDAO.save(new User(null, username));
    }

    public void updateUser(Long id, String newUsername) {
        User user = new User(id, newUsername);
        usersDAO.update(user);
    }

    public void deleteUser(Long id) {
        usersDAO.delete(id);
    }

    public Optional<User> getUserById(Long id) {
        return usersDAO.findById(id);
    }

    public List<User> getAllUsers() {
        return usersDAO.findAll();
    }
}

