package org.example.dao;
import org.example.entity.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersDAO {
    private final DataSource dataSource;

    public UsersDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(User user) {
        //noinspection SqlNoDataSourceInspection
        String sql = "INSERT INTO tbl_users (username) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.executeUpdate();
        } catch (SQLException e) { throw new IllegalArgumentException("Не удалось сохранить пользователя в БД", e);}
    }

    public void delete(Long id) {
        //noinspection SqlNoDataSourceInspection
        String sql = "DELETE FROM tbl_users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new IllegalArgumentException("Не удалось удалить пользователя из БД", e); }
    }

    public void update(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Невозможно обновить пользователя без ID");
        }
        //noinspection SqlNoDataSourceInspection
        String sql = "UPDATE tbl_users SET username = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setLong(2, user.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Пользователь с ID " + user.getId() + " не найден для обновления.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Не удалось обновить пользователя в БД", e);
        }
    }

    public Optional<User> findById(Long id) {
        //noinspection SqlNoDataSourceInspection
        String sql = "SELECT id, username FROM tbl_users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(rs.getLong("id"), rs.getString("username")));
                }
            }
        } catch (SQLException e) { throw new IllegalArgumentException("Не удалось получить пользователя из БД", e);}
        return Optional.empty();
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        //noinspection SqlNoDataSourceInspection
        String sql = "SELECT id, username FROM tbl_users";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("username")));
            }
        } catch (SQLException e) { throw new IllegalArgumentException("Не удалось получить всех пользователей из БД");}
        return users;
    }
}
