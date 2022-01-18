package ua.com.alevel.dto.response;

import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.Client;

public class ClientResponseDto extends ResponseDto {

    private Integer accountCount;
    private String email;

    public ClientResponseDto(Client client) {
        setEmail(client.getEmail());
        setId(client.getId());
        setName(client.getFullName());
        this.accountCount = client.getAccounts().size();
    }

    public ClientResponseDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(Integer accountCount) {
        this.accountCount = accountCount;
    }
}
