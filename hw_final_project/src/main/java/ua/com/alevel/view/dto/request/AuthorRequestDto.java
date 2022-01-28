package ua.com.alevel.view.dto.request;

import ua.com.alevel.view.dto.response.AuthorResponseDto;

import java.sql.Date;
import java.util.List;

public class AuthorRequestDto extends RequestWithIdDto {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Date deathDate;
    private List<String> isbnList;

    public AuthorRequestDto() {
    }

    public AuthorRequestDto(AuthorResponseDto responseDto) {
        this.firstName = responseDto.getFirstName();
        this.lastName = responseDto.getLastName();
        this.birthDate = responseDto.getBirthDate();
        this.deathDate = responseDto.getDeathDate();
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

    public void setBirthDate(String birthDate) {
        this.birthDate = toSqlDate(birthDate);
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = toSqlDate(deathDate);
    }

    public List<String> getIsbnList() {
        return isbnList;
    }

    public void setIsbnList(List<String> isbnList) {
        this.isbnList = isbnList;
    }
}
