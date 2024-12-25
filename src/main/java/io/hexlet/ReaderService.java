package io.hexlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReaderService {
    private final Connection connection;

    public ReaderService(Connection connection) {
        this.connection = connection;
    }

    public void addReader(String name, String email) {
        String query = "INSERT INTO readers (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.executeUpdate();
            System.out.println("Читатель добавлен.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении читателя", e);
        }
    }

    public List<String> getAllReaders() {
        String query = "SELECT * FROM readers";
        List<String> readers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                readers.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка читателей", e);
        }
        return readers;
    }

    public String findReaderByEmail(String email) {
        String query = "SELECT * FROM readers WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске читателя", e);
        }
        return null;
    }

    public void deleteReader(int id) {
        String query = "DELETE FROM readers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Читатель удален.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении читателя", e);
        }
    }
}
