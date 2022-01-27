package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Author;

import java.sql.Date;

public class AuthorResponseDto extends ResponseWithIdDto {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Date deathDate;
    private Integer booksCount;

    public AuthorResponseDto(Author author) {
        firstName = author.getFirstName();
        lastName = author.getLastName();
        birthDate = author.getBirthDate();
        deathDate = author.getDeathDate();
        this.setId(author.getId());
        this.setCreated(author.getCreated());
        this.setUpdated(author.getUpdated());
        this.setVisible(author.getVisible());
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public Integer getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Integer booksCount) {
        this.booksCount = booksCount;
    }
}
