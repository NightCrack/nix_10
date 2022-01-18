package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.ClientDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;
import ua.com.alevel.entity.Client;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.util.WebResponseUtil;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void create(Client client) {
        clientDao.create(client);
    }

    @Override
    public void update(Client client) {
        clientDao.update(client);
    }

    @Override
    public void delete(Long id) {
        clientDao.delete(id);
    }

    @Override
    public Client findById(Long id) {
        return clientDao.findById(id);
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        DataTableResponse<Client> dataTableResponse = clientDao.findAll(request);
        long count = clientDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public BaseEntity findForeignEntityById(Long id) {
        return null;
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long clientId) {
        return clientDao.findAllForeignEntriesById(clientId);
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
