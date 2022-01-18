package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.dto.request.ActionRequestDto;
import ua.com.alevel.dto.response.ActionResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.entity.Account;
import ua.com.alevel.entity.Action;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;
import ua.com.alevel.exception.FundsException;
import ua.com.alevel.facade.ActionFacade;
import ua.com.alevel.service.ActionService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service

public class ActionFacadeImpl implements ActionFacade {

    private final ActionService actionService;

    public ActionFacadeImpl(ActionService actionService) {
        this.actionService = actionService;
    }

    @Override
    public void create(ActionRequestDto actionRequestDto) throws SQLException {
        BigDecimal amount = actionRequestDto.getAmount();
        Account account = actionRequestDto.getAccount();
        ActionType actionType = findEntityByParameter("type_name", actionRequestDto.getActionType());
        try {
            actionService.balanceChange(amount, account.getId(), actionType.getTransactionType());
        } catch (FundsException e) {
            throw new FundsException(e.getMessage());
        }
        Action action = new Action();
        action.setCreated(Instant.now());
        action.setActionType(actionType);
        action.setAmount(amount);
        action.setAccount(account);
        action.setCreated(Instant.now());
        action.setUpdated(action.getCreated());
        actionService.create(action);
        actionService.update(action);
    }

    @Override
    public void update(ActionRequestDto actionRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ActionResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageData<ActionResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Action> tableResponse = actionService.findAll(dataTableRequest);
        List<ActionResponseDto> operations = tableResponse
                .getEntries()
                .stream()
                .map(ActionResponseDto::new)
                .toList();
        PageData<ActionResponseDto> pageData = (PageData<ActionResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(operations);
        return pageData;
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
        return actionService.findEntityByParameter(parameterName, parameterValue);
    }

    @Override
    public String writeOutByForeignId(Long accountId) {
        return actionService.writeOutByForeignId(accountId);
    }

    @Override
    public String writeOutBySecondForeignId(Long clientId) {
        return actionService.writeOutBySecondForeignId(clientId);
    }

}
