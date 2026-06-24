package org.example;

import org.example.dao.AppConfig;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UserService userService = context.getBean(UserService.class);

            System.out.println("--- Создание пользователей --- ");
            userService.createUser("Леша");
            userService.createUser("Паша");
            userService.createUser("Валера");

            System.out.println("\n--- Список всех пользователей ---");
            List<User> users = userService.getAllUsers();
            users.forEach(System.out::println);

            System.out.println("\n--- Получение первого пользователя ---");
            Optional<User> userOptional = userService.getUserById(users.getFirst().getId());
            userOptional.ifPresent(u -> System.out.println("Найден пользователь: " + u));

            System.out.println("\n--- Обновление первого пользователя ---");


            userOptional.ifPresent(u ->System.out.println("До обновления: " + u));
            userOptional.ifPresent(u ->userService.updateUser(u.getId(), "Глория"));

            System.out.println("\n--- Удаление пользователя последнего---");
            User user = users.getLast();
            System.out.println("До удаления: " + user);
            userService.deleteUser(user.getId());

            System.out.println("\n--- Итоговый список пользователей---");
            userService.getAllUsers().forEach(System.out::println);
        }
    }
}

