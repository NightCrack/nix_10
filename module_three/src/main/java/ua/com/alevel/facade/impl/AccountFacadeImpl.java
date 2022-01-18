package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.dto.request.AccountRequestDto;
import ua.com.alevel.dto.response.AccountResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.Client;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final ClientService clientService;

    public AccountFacadeImpl(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public void create(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100));
        account.setClient(clientService.findById(accountRequestDto.getClientId()));
        account.setAccountName(accountRequestDto.getAccountName());
        account.setCreated(Instant.now());
        account.setCreated(account.getCreated());
        accountService.create(account);
    }

    @Override
    public void update(AccountRequestDto accountRequestDto, Long id) {
        Account account = accountService.findById(id);
        account.setAccountName(accountRequestDto.getAccountName());
        account.setUpdated(Instant.now());
        accountService.update(account);
    }

    @Override
    public void delete(Long id) {
        accountService.delete(id);
    }

    @Override
    public AccountResponseDto findById(Long id) {
        return new AccountResponseDto(accountService.findById(id));
    }

    @Override
    public PageData<AccountResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Account> tableResponse = accountService.findAll(dataTableRequest);
        List<AccountResponseDto> accounts = tableResponse
                .getEntries()
                .stream()
                .map(AccountResponseDto::new)
                .toList();
        PageData<AccountResponseDto> pageData = (PageData<AccountResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(accounts);
        return pageData;
    }

    @Override
    public Client findForeignEntityById(Long accountId) {
        return accountService.findForeignEntityById(accountId);
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
    public String writeOutByForeignId(Long foreignId) {
        return null;
    }

    @Override
    public String writeOutBySecondForeignId(Long secondForeignId) {
        return null;
    }
}
