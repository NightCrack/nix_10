package ua.com.alevel.dto.request;

import ua.com.alevel.dto.RequestDto;

public class AccountRequestDto extends RequestDto {

    private String accountName;
    private Long clientId;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
