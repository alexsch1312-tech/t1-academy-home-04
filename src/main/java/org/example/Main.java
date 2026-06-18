package org.example;

import org.example.dao.UsersDAO;

import org.example.entity.Users;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UsersDAO usersDAO = context.getBean(UsersDAO.class);

        System.out.println("--- Пользователи ---");
        Users users1 = new Users("Паша");
        Users users2 = new Users("Лева");
        Users users3 = new Users("Cева");

        usersDAO.saveUsers(users1);
        usersDAO.saveUsers(users2);
        usersDAO.saveUsers(users3);

        System.out.println("\n--- Чтение всех пользователей ---");
        List<Users> usersList = usersDAO.getAllUsers();
        usersList.forEach(System.out::println);

        System.out.println("\n--- Обновление пользователя ---");
        if (!usersList.isEmpty()) {
            Users firstUsers = usersList.get(0);
            firstUsers.setUserName("Глория");
            usersDAO.updateUsers(firstUsers);
            System.out.println("Обновленный пользователь: " + usersDAO.getUsersById(firstUsers.getId()));
        }

        System.out.println("\n--- Удаление второго пользователя ---");
        if (usersList.size() > 1) {
            Long idToDelete = usersList.get(1).getId();
            usersDAO.deleteUsers(idToDelete);
        }

        System.out.println("\n--- Итоговый список пользователей ---");
        usersDAO.getAllUsers().forEach(System.out::println);

        usersDAO.shutdown();
        context.close();
    }
}
