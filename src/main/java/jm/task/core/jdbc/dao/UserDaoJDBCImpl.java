package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.sql.Select;

import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;


    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    public void createUsersTable() {
        try (Statement state = connection.createStatement()) {
            String sql = ("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                            " name VARCHAR(255)," +
                            " lastname VARCHAR(255)," +
                            " age TINYINT)");
                    state.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }

    public void dropUsersTable() {
        try (Statement state = connection.createStatement()) {
            state.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping table", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users" +
                "(name, lastName, age) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement state = connection.createStatement()) {
            ResultSet rs = state.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                users.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all users", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement state = connection.createStatement()) {
            state.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException("Error cleaning table", e);
        }
    }
}
