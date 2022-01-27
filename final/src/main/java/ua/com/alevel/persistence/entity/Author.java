package ua.com.alevel.persistence.entity;


import ua.com.alevel.view.dto.request.AuthorRequestDto;

import java.sql.Date;

public final class Author extends WithIdEntity {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Date deathDate;

    public Author() {
    }

    public Author(AuthorRequestDto authorRequestDto) {
        firstName = authorRequestDto.getFirstName();
        lastName = authorRequestDto.getLastName();
        birthDate = authorRequestDto.getBirthDate();
        deathDate = authorRequestDto.getDeathDate();
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
}
