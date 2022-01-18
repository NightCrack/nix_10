package ua.com.alevel.dto.response;

import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.Action;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ActionResponseDto extends ResponseDto {
    private String actionType;
    private String deposit;
    private BigDecimal amount;
    private String accountName;

    public ActionResponseDto(Action action) {
        setId(action.getId());
        setCreated(action.getCreated());
        this.actionType = action.getActionType().getTypeName().name();
        this.deposit = action.getActionType().getTransactionType() ? "+" : "-";
        this.amount = action.getAmount().divide(BigDecimal.valueOf(100), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP);
        this.accountName = action.getAccount().getAccountName();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
