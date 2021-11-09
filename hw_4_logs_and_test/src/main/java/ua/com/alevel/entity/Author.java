package ua.com.alevel.entity;

import ua.com.alevel.db.impl.BooksImpl;
import ua.com.alevel.util.CustomUtil;

import java.util.Arrays;
import java.util.Date;

public class Author extends BaseEntity {

    private final int arraySize = 10;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfDeath;
    private String[] bookIsbns = new String[arraySize];

    public String[] getBookIsbns() {
        return bookIsbns;
    }

    public void setBookIsbns(String[] bookIsbns) {
        this.bookIsbns = bookIsbns;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + ' ' + this.lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
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
        return "(ID: " + this.getId() + ") " +
                getFullName() + " (" +
                CustomUtil.dateToString(dateOfBirth) +
                '-' + CustomUtil.dateToString(dateOfDeath) +
                ")";
    }

    @Override
    public String toString() {
        return getFullName() +
                System.lineSeparator() +
                "dateOfBirth: " + CustomUtil.dateToString(dateOfBirth) +
                System.lineSeparator() +
                "dateOfDeath: " + CustomUtil.dateToString(dateOfDeath) +
                System.lineSeparator() +
                "Books: " + System.lineSeparator() +
                CustomUtil.composeStringArray(books());
    }
}
