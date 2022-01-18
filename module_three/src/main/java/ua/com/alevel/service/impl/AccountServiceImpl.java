package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.AccountDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.Client;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.util.WebResponseUtil;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void create(Account account) {
        accountDao.create(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Long id) {
        accountDao.delete(id);
    }

    @Override
    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        DataTableResponse<Account> dataTableResponse = accountDao.findAll(request);
        long count = accountDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Client findForeignEntityById(Long accountId) {
        return accountDao.findForeignEntityById(accountId);
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long id) {
        return null;
    }

    @Override
    public ActionType findEntityByParameter(String parameterName, String parameterValue) {
        return null;
    }

    @Override
    public void balanceChange(BigDecimal amount, Long actionId, Boolean deposit) {

    }

    @Override
    public String writeOutByForeignId(Long foreignId) {
        return null;
    }

    @Override
    public String writeOutBySecondForeignId(Long secondForeignId) {
        return null;
    }
}
