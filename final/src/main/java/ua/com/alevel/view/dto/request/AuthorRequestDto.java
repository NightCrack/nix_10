package ua.com.alevel.view.dto.request;

import java.sql.Date;

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
        this.dateOfBirth = dateFormatting(dateOfBirth);
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateFormatting(dateOfDeath);
    }
}
