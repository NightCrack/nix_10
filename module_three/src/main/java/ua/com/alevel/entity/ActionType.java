package ua.com.alevel.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action_types")
public class ActionType extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type_name")
    private typeName typeName;
    @Column(name = "deposit")
    private Boolean transactionType;
    @OneToMany(mappedBy = "actionType")
    private Set<Action> actions;

    public ActionType() {
        super();
        this.actions = new HashSet<>();
    }

    public ActionType.typeName getTypeName() {
        return typeName;
    }

    public void setTypeName(ActionType.typeName typeName) {
        this.typeName = typeName;
    }

    public Boolean getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Boolean transactionType) {
        this.transactionType = transactionType;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @AttributeOverride(name = "id", column = @Column(name = "action_type_id"))

    public enum typeName {
        refuel_tank("-"),
        top_up_card("+"),
        debit_from_card("-"),
        buy_something("-"),
        deposit("+"),
        charity("-");

        private String transactionType;

        typeName(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTransactionType() {
            return transactionType;
        }
    }
}
