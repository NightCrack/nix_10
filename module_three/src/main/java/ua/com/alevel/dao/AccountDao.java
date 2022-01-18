package ua.com.alevel.dao;

import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.Client;

public interface AccountDao extends DependentDao<Account, Client> {
}
