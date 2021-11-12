package ua.com.alevel.entity;

import ua.com.alevel.db.impl.BooksImpl;
import ua.com.alevel.util.CustomUtil;

import java.util.Arrays;

public class Genre extends BaseEntity {

    private final int arraySize = 10;
    private String genreName;
    private String[] isbns = new String[arraySize];

    public String[] getIsbns() {
        return isbns;
    }

    public void setIsbns(String[] isbns) {
        this.isbns = isbns;
    }

    public void addBook(String isbn) {
        int booksIsbnAmount = 0;
        for (String value : isbns) {
            if (value != null) {
                booksIsbnAmount++;
            }
        }
        if (!(isbns.length > booksIsbnAmount)) {
            isbns = Arrays.copyOf(isbns, isbns.length + arraySize);
        }
        isbns[booksIsbnAmount] = isbn;
    }

    public boolean removeBook(String isbn) {
        for (int position = 0; position < isbns.length; position++) {
            if ((isbns[position] != null) && (isbns[position].equals(isbn))) {
                for (; position < isbns.length - 1; position++) {
                    isbns[position] = isbns[position + 1];
                }
                isbns = Arrays.copyOf(isbns, (isbns.length - 1));
                return true;
            }
        }
        return false;
    }

    public String getName() {
        if (genreName != null) {
            return genreName;
        }
        return null;
    }

    public void setName(String genreName) {
        this.genreName = genreName;
    }

    private String[] books() {
        String[] books = new String[0];
        for (int index = 0; index < this.isbns.length; index++) {
            if (this.isbns[index] != null) {
                books = Arrays.copyOf(books, books.length + 1);
                books[books.length - 1] = BooksImpl.getInstance().findById(this.isbns[index]).toStringBrief();
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
