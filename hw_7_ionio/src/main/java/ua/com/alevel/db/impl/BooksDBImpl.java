package ua.com.alevel.db.impl;

import custom.annotations.Path;
import custom.annotations.Service;
import ua.com.alevel.db.BooksDB;
import ua.com.alevel.entity.Book;
import ua.com.alevel.util.CSVTranslator;
import ua.com.alevel.util.FileOps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BooksDBImpl implements BooksDB<String> {

    @Path
    private static String tablePath;
    private List<Book> books;
    private String classId = "isbn";

    @Override
    public String getPath() {
        return this.tablePath;
    }

    @Override
    public boolean create(Book book) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        if (idList.stream().anyMatch(entry -> entry.equals(book.getIsbn()))) {
            return false;
        }
        return FileOps.write(tablePath, CSVTranslator.objectsToString(Collections.singletonList(book)), true);
    }

    @Override
    public boolean update(Book book) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        int entryLine = idList.indexOf(book.getIsbn()) + 1;
        books = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), Book.class);
        books.addAll(entries);
        books.add(book);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), Book.class);
        books.addAll(entries);
        FileOps.write(tablePath, CSVTranslator.buildOutput(Collections.singletonList(CSVTranslator.buildHeader(Book.class))), false);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(books), true);
    }

    @Override
    public boolean delete(String isbn) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        if (!idList.contains(isbn)) {
            return false;
        }
        int entryLine = idList.indexOf(isbn) + 1;
        books = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), Book.class);
        books.addAll(entries);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), Book.class);
        books.addAll(entries);
        FileOps.write(tablePath, CSVTranslator
                .buildOutput(Collections
                        .singletonList(CSVTranslator
                                .buildHeader(Book.class))), false);
        if (books.size() > 0) {
            return FileOps.write(tablePath, CSVTranslator.objectsToString(books), true);
        }
        return true;
    }

    @Override
    public Book findById(String isbn) {
        List entry = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), Book.class);
        try {
            return ((List<Book>) entry).stream()
                    .filter(book -> book.getIsbn().equals(isbn))
                    .findFirst().get();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<Book> findAll() {
        List formattedEntries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), Book.class);
        return formattedEntries;
    }

}
