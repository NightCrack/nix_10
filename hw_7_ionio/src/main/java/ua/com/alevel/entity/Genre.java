package ua.com.alevel.entity;

import custom.annotations.Entity;
import custom.annotations.SetClass;
import ua.com.alevel.db.BooksDB;

import java.util.ArrayList;
import java.util.List;

@Entity
public final class Genre extends WithIDEntity {

    @SetClass
    private transient static Class booksDB;

    private List<String> isbnList = new ArrayList<>();
    private String name;

    public List<String> getIsbnList() {
        return isbnList;
    }

    public void setIsbnList(List<String> isbnList) {
        this.isbnList = isbnList;
    }

    public Genre addBook(String isbn) {
        isbnList.add(isbn);
        return this;
    }

    public boolean removeBook(String isbn) {
        return isbnList.remove(isbn);
    }

    public String getName() {
        return this.name;
    }

    public boolean setName(String genreName) {
        try {
            this.name = genreName;
            return true;
        } catch (Exception exception) {
            return false;
        }
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
        StringBuilder returnValue = new StringBuilder();
        for (String isbn : isbnList) {
            if (!isbn.isBlank()) {
                returnValue.append(System.lineSeparator())
                        .append(findBook(isbn).getName());
            }
        }
        return returnValue.toString();
    }

    public String toStringBrief() {
        return "(ID: " + this.getId() + ") " + name;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                name +
                System.lineSeparator() +
                "Books: " + books();
    }
}
