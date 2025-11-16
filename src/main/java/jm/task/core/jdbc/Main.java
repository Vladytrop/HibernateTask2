package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("John", "Doe", (byte) 30);
        userService.saveUser("Jane", "Doe", (byte) 25);
        userService.saveUser("Bob", "Smith", (byte) 40);
        userService.saveUser("Alice", "Smith", (byte) 35);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
