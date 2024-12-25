package io.hexlet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final Connection connection;

    public BookService(Connection connection) {
        this.connection = connection;
    }

    public void addBook(String title, String author, Date publishedDate, String isbn) {
        String query = "INSERT INTO books (title, author, published_date, isbn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setDate(3, publishedDate);
            statement.setString(4, isbn);
            statement.executeUpdate();
            System.out.println("Книга добавлена.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении книги", e);
        }
    }

    public List<String> getAllBooks() {
        String query = "SELECT * FROM books";
        List<String> books = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                books.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка книг", e);
        }
        return books;
    }

    public String findBookByTitle(String title) {
        String query = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("title");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске книги", e);
        }
        return null;
    }

    public void deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Книга удалена.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении книги", e);
        }
    }

    public void clearBooks() {
        String query = "DELETE FROM books";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Все книги удалены.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке таблицы books", e);
        }
    }
}
