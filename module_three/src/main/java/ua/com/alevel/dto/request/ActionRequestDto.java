package ua.com.alevel.dto.request;

import ua.com.alevel.dto.RequestDto;
import ua.com.alevel.entity.Account;
import ua.com.alevel.exception.InputException;

import java.math.BigDecimal;

public class ActionRequestDto extends RequestDto {

    private String actionType;
    private BigDecimal amount;
    private Account account;

    public BigDecimal getAmount() {
        try {
            return amount;
        } catch (NumberFormatException e) {
            throw new InputException("The format of input is: \"0,00\"");
        }
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
