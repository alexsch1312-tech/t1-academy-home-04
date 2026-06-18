package org.example.dao;

import org.example.entity.Users;
import org.hibernate.Session;

import java.util.List;

import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
public class UsersDAO {

    public void saveUsers(Users users) {
        Transaction transaction = null;
        try (Session session = SessionCreator.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(users); // Сохраняем в базу
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Users getUsersById(Long id) {
        try (Session session = SessionCreator.getSessionFactory().openSession()) {
            return session.get(Users.class, id); // Поиск по первичному ключу
        }
    }

    public List<Users> getAllUsers() {
        try (Session session = SessionCreator.getSessionFactory().openSession()) {
            return session.createQuery("from org.example.entity.Users", Users.class).list();
        }
    }

    public void updateUsers(Users users) {
        Transaction transaction = null;
        try (Session session = SessionCreator.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(users); // Обновляем существующий объект
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteUsers(Long id) {
        Transaction transaction = null;
        try (Session session = SessionCreator.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Users users = session.get(Users.class, id);
            if (users != null) {
                session.remove(users);
                System.out.println("Пользователь с ID " + id + " успешно удален.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void shutdown() {
        SessionCreator.shutdown();
    }
}