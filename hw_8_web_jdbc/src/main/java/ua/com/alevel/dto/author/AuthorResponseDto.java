package ua.com.alevel.dto.author;

import ua.com.alevel.dto.ResponseWithIdDto;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;

import java.util.Date;
import java.util.List;

public class AuthorResponseDto extends ResponseWithIdDto {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfDeath;
    private List<Book> books;

    public AuthorResponseDto(Author author) {
        this.setFirstName(author.getFirstName());
        this.setLastName(author.getLastName());
        this.setDateOfBirth(author.getDateOfBirth());
        this.setDateOfDeath(author.getDateOfDeath());
        this.setBooks(author.getBooks());
        this.setCreated(author.getCreated());
        this.setUpdated(author.getUpdated());
        this.setId(author.getId());
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
