package ua.com.alevel.entity;

import custom.annotations.Entity;
import custom.annotations.SetClass;
import ua.com.alevel.db.BooksDB;
import ua.com.alevel.util.CustomUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public final class Author extends WithIDEntity {

    @SetClass
    private transient static Class booksDB;

    private List<String> isbn = new ArrayList<>();
    private Date dateOfDeath;
    private Date dateOfBirth;
    private String lastName;
    private String firstName;

    public List<String> getIsbn() {
        return isbn;
    }

    public void setIsbn(List<String> isbn) {
        this.isbn = isbn;
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

    public Author addBook(String isbn) {
        this.isbn.add(isbn);
        return this;
    }

    public boolean removeBook(String isbn) {
        return this.isbn.remove(isbn);
    }

    public String getName() {
        if ((firstName != null) && (lastName != null)) {
            return this.firstName + ' ' + this.lastName;
        }
        return null;
    }

    private Book findBook(String isbn) {
        try {
            return (Book) ((BooksDB) booksDB
                    .getDeclaredConstructor()
                    .newInstance())
                    .findById(isbn);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    private String books() {
        StringBuilder returnString = new StringBuilder();
        for (String isbn : this.isbn) {
            if (!isbn.isBlank()) {
                returnString.append(System.lineSeparator())
                        .append(findBook(isbn).getName());
            }
        }
        return returnString.toString();
    }

    public String toStringBrief() {
        return "(ID: " + this.getId() + ") " +
                getName() + " (" +
                CustomUtil.dateToString(dateOfBirth) +
                '-' + CustomUtil.dateToString(dateOfDeath) +
                ")";
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                getName() +
                System.lineSeparator() +
                "dateOfBirth: " + CustomUtil.dateToString(dateOfBirth) +
                System.lineSeparator() +
                "dateOfDeath: " + CustomUtil.dateToString(dateOfDeath) +
                System.lineSeparator() +
                "Books: " + books();
    }
}
