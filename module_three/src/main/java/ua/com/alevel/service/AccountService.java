package ua.com.alevel.service;

import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.Client;

public interface AccountService extends DependentService<Account, Client> {
}
