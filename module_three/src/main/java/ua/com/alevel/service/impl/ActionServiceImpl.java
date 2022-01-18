package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.AccountDao;
import ua.com.alevel.dao.ActionDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.Action;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;
import ua.com.alevel.exception.FundsException;
import ua.com.alevel.service.ActionService;
import ua.com.alevel.util.CSVTranslator;
import ua.com.alevel.util.WebResponseUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Map;

@Service
public class ActionServiceImpl implements ActionService {

    private static final String fileSeparator = FileSystems.getDefault().getSeparator();

    private final ActionDao actionDao;
    private final AccountDao accountDao;

    public ActionServiceImpl(ActionDao actionDao, AccountDao accountDao) {
        this.actionDao = actionDao;
        this.accountDao = accountDao;
    }

    @Override
    public void create(Action action) {

        actionDao.create(action);
    }

    @Override
    public void update(Action action) {
        actionDao.update(action);
    }

    @Override
    public void delete(Long id) {
        actionDao.delete(id);
    }

    @Override
    public Action findById(Long id) {
        return actionDao.findById(id);
    }

    @Override
    public DataTableResponse<Action> findAll(DataTableRequest request) {
        DataTableResponse<Action> dataTableResponse = actionDao.findAll(request);
        long count = actionDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public BaseEntity findForeignEntityById(Long id) {
        return null;
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long id) {
        return null;
    }

    @Override
    public ActionType findEntityByParameter(String parameterName, String parameterValue) {
        return actionDao.findEntityByParameter(parameterName, parameterValue);
    }

    @Override
    public void balanceChange(BigDecimal amount, Long accountId, Boolean deposit) {
        Account account = accountDao.findById(accountId);
        BigDecimal balance = account.getBalance();
        if (!deposit && (balance.subtract(amount).floatValue()) < 0) {
            System.out.println("balance = " + balance);
            System.out.println("sum = " + amount);
            throw new FundsException("NOT ENOUGH FUNDS");
        } else {
            BigDecimal result = (deposit ? balance.add(amount) : balance.subtract(amount)).setScale(2, RoundingMode.HALF_UP);
            System.out.println("balance = " + result);
            account.setBalance(result);
            accountDao.update(account);
        }
    }

    @Override
    public String writeOutByForeignId(Long accountId) {
        List<Action> actions = actionDao.findAllByForeignId(accountId);
        List<String[]> outputList = actions.stream().map(action ->
                new String[]{action.getAccount().getClient().getEmail(),
                        action.getActionType().getTypeName().name(),
                        action.getActionType().getTransactionType() ? "+" : "-",
                        action.getAmount().toString()}
        ).toList();
        return CSVTranslator.buildOutput(outputList);
    }

    @Override
    public String writeOutBySecondForeignId(Long clientId) {
        List<Action> actions = actionDao.findAllBySecondForeignId(clientId);
        List<String[]> outputList = actions.stream().map(action ->
                new String[]{action.getAccount().getAccountName(),
                        action.getAccount().getClient().getEmail(),
                        action.getActionType().getTypeName().name(),
                        action.getActionType().getTransactionType() ? "+" : "-",
                        action.getAmount().toString()}
        ).toList();
        return CSVTranslator.buildOutput(outputList);
    }
}
