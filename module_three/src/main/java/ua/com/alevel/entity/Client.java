package ua.com.alevel.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    @AttributeOverride(name = "id", column = @Column(name = "client_id"))

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @OneToMany(mappedBy = "client", cascade = {
            CascadeType.REMOVE
    })
    private Set<Account> accounts;

    public Client() {
        super();
        this.accounts = new HashSet<>();
    }

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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
