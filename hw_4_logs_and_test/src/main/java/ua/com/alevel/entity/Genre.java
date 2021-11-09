package ua.com.alevel.entity;

import ua.com.alevel.db.impl.BooksImpl;
import ua.com.alevel.util.CustomUtil;

import java.util.Arrays;

public class Genre extends BaseEntity {

    private final int arraySize = 10;
    private String genreName;
    private String[] bookIsbns = new String[arraySize];

    public void addBook(String isbn) {
        int booksIsbnAmount = 0;
        for (String value : bookIsbns) {
            if (value != null) {
                booksIsbnAmount++;
            }
        }
        if (!(bookIsbns.length > booksIsbnAmount)) {
            bookIsbns = Arrays.copyOf(bookIsbns, bookIsbns.length + arraySize);
        }
        bookIsbns[booksIsbnAmount] = isbn;
    }

    public boolean removeBook(String isbn) {
        for (int position = 0; position < bookIsbns.length; position++) {
            if (bookIsbns[position].equals(isbn)) {
                for (; position < bookIsbns.length - 1; position++) {
                    bookIsbns[position] = bookIsbns[position + 1];
                }
                bookIsbns = Arrays.copyOf(bookIsbns, (bookIsbns.length - 1));
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return genreName;
    }

    public void setName(String genreName) {
        this.genreName = genreName;
    }

    private String[] books() {
        String[] books = new String[0];
        for (int index = 0; index < this.bookIsbns.length; index++) {
            if (this.bookIsbns[index] != null) {
                books = Arrays.copyOf(books, books.length + 1);
                books[books.length - 1] = BooksImpl.getInstance().findById(this.bookIsbns[index]).toStringBrief();
            }
        }
        return books;
    }

    public String toStringBrief() {
        return "(ID: " + this.getId() + ") " + genreName;
    }

    @Override
    public String toString() {
        return genreName +
                System.lineSeparator() +
                "Books: " + System.lineSeparator() +
                CustomUtil.composeStringArray(books());
    }
}
