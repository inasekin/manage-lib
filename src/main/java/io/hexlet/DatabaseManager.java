package io.hexlet;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "ivannasekin";
    private static final String PASSWORD = "hexlet!";
    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение с базой данных установлено.");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к базе данных", e);
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Соединение с базой данных закрыто.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения с базой данных", e);
        }
    }
}
