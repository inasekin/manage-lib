package io.hexlet;

import java.sql.Date;

public class Application {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.connect();

        BookService bookService = new BookService(dbManager.getConnection());
        ReaderService readerService = new ReaderService(dbManager.getConnection());

        bookService.addBook("Effective Java", "Joshua Bloch", Date.valueOf("2018-01-01"), "1234567890");
        System.out.println(bookService.getAllBooks());
        System.out.println(bookService.findBookByTitle("Effective Java"));
        bookService.deleteBook(1);

        readerService.addReader("John Doe", "john.doe@example.com");
        System.out.println(readerService.getAllReaders());
        System.out.println(readerService.findReaderByEmail("john.doe@example.com"));
        readerService.deleteReader(1);

        dbManager.disconnect();
    }
}
