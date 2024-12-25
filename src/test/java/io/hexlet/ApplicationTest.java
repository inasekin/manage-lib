package io.hexlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationTest {
    private DatabaseManager dbManager;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        dbManager = new DatabaseManager();
        dbManager.connect();
        bookService = new BookService(dbManager.getConnection());

        // Очистка таблицы перед каждым тестом
        bookService.clearBooks();
    }

    @AfterEach
    void tearDown() {
        dbManager.disconnect();
    }

    @Test
    @DisplayName("Добавление и получение книги работает корректно")
    void testAddAndGetBook() {
        bookService.addBook("Effective Java", "Joshua Bloch", Date.valueOf("2018-01-01"), "1234567890");

        List<String> books = bookService.getAllBooks();
        assertEquals(1, books.size(), "Должна быть добавлена одна книга");
        assertTrue(books.contains("Effective Java"), "Добавленная книга должна быть в списке");
    }

    @Test
    @DisplayName("Поиск книги по названию работает корректно")
    void testFindBookByTitle() {
        bookService.addBook("Effective Java", "Joshua Bloch", Date.valueOf("2018-01-01"), "1234567890");

        String foundBook = bookService.findBookByTitle("Effective Java");
        assertEquals("Effective Java", foundBook, "Поиск книги по названию должен возвращать правильное значение");
    }
}
