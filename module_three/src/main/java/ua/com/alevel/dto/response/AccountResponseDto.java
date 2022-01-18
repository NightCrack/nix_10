package ua.com.alevel.dto.response;

import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.Account;

import java.math.BigDecimal;

public class AccountResponseDto extends ResponseDto {

    private BigDecimal balance;
    private String accountName;
    private Long Id;
    private String clientEmail;

    public AccountResponseDto() {
    }

    public AccountResponseDto(Account account) {
        setId(account.getId());
        setCreated(account.getCreated());
        this.accountName = account.getAccountName();
        this.clientEmail = account.getClient().getEmail();
        this.balance = account.getBalance();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public Long getId() {
        return Id;
    }

    @Override
    public void setId(Long id) {
        Id = id;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}
