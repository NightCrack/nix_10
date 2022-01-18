package ua.com.alevel.dto.request;

import ua.com.alevel.dto.RequestDto;

import java.util.Set;

public class ClientRequestDto extends RequestDto {

    private String fullName;

    private String email;

    private Set<Long> accountIds;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Long> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(Set<Long> accountIds) {
        this.accountIds = accountIds;
    }
}
