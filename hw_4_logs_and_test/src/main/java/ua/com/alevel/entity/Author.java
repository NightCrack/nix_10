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
    private String[] isbn = new String[arraySize];

    public String[] getIsbns() {
        if (isbn != null) {
            return isbn;
        }
        return null;
    }

    public void setIsbn(String[] isbn) {
        this.isbn = isbn;
    }

    public void addBook(String isbn) {
        int booksIsbnAmount = 0;
        for (String value : this.isbn) {
            if (value != null) {
                booksIsbnAmount++;
            }
        }
        if (!(this.isbn.length > booksIsbnAmount)) {
            this.isbn = Arrays.copyOf(this.isbn, this.isbn.length + arraySize);
        }
        this.isbn[booksIsbnAmount] = isbn;
    }

    public boolean removeBook(String isbn) {
        for (int position = 0; position < this.isbn.length; position++) {
            if ((this.isbn[position] != null) && (this.isbn[position].equals(isbn))) {
                for (; position < this.isbn.length - 1; position++) {
                    this.isbn[position] = this.isbn[position + 1];
                }
                this.isbn = Arrays.copyOf(this.isbn, (this.isbn.length - 1));
                return true;
            }
        }
        return false;
    }

    public String getFirstName() {
        if (firstName != null) {
            return firstName;
        }
        return null;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (lastName != null) {
            return lastName;
        }
        return null;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        if ((firstName != null) && (lastName != null)) {
            return this.firstName + ' ' + this.lastName;
        }
        return null;
    }

    public Date getDateOfBirth() {
        if (dateOfBirth != null) {
            return dateOfBirth;
        }
        return null;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfDeath() {
        if (dateOfDeath != null) {
            return dateOfDeath;
        }
        return null;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    private String[] books() {
        String[] books = new String[0];
        for (int index = 0; index < this.isbn.length; index++) {
            if (this.isbn[index] != null) {
                books = Arrays.copyOf(books, books.length + 1);
                books[books.length - 1] = BooksImpl.getInstance().findById(this.isbn[index]).toStringBrief();
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
