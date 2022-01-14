package ua.com.alevel.dto.author;

import ua.com.alevel.dto.RequestWithIdDto;

import java.util.Date;

public class AuthorRequestDto extends RequestWithIdDto {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfDeath;

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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateFromString(dateOfBirth);
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateFromString(dateOfDeath);
    }
}
