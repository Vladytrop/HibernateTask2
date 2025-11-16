package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соединения с БД
    Connection connection;
    public Util() {
        this.connection = Util.getConnection();
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=" +
                            "UTC&allowPublicKeyRetrieval=true",
                    "root", "12345");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
