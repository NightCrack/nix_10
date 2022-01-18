package ua.com.alevel.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "actions")
public class Action extends BaseEntity {

    @AttributeOverride(name = "id", column = @Column(name = "action_id"))

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_type_id")
    private ActionType actionType;

    private BigDecimal amount;

    public Action() {
        super();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
