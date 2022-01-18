package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.dto.request.ClientRequestDto;
import ua.com.alevel.dto.response.ClientResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;
import ua.com.alevel.entity.Client;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ClientFacadeImpl implements ClientFacade {

    private final AccountService accountService;
    private final ClientService clientService;

    public ClientFacadeImpl(AccountService accountService, ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public void create(ClientRequestDto clientRequestDto) {
        Client client = new Client();
        client.setFullName(clientRequestDto.getFullName());
        client.setEmail(clientRequestDto.getEmail());
        client.setCreated(Instant.now());
        client.setUpdated(client.getCreated());
        clientService.create(client);
    }

    @Override
    public void update(ClientRequestDto clientRequestDto, Long id) {
        Client client = clientService.findById(id);
        client.setEmail(clientRequestDto.getEmail());
        client.setUpdated(Instant.now());
        clientService.update(client);
    }

    @Override
    public void delete(Long id) {
        clientService.delete(id);
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return new ClientResponseDto(clientService.findById(id));
    }

    @Override
    public PageData<ClientResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Client> tableResponse = clientService.findAll(dataTableRequest);
        List<ClientResponseDto> clients = tableResponse
                .getEntries()
                .stream()
                .map(ClientResponseDto::new)
                .toList();
        PageData<ClientResponseDto> pageData = (PageData<ClientResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(clients);
        return pageData;
    }

    @Override
    public BaseEntity findForeignEntityById(Long id) {
        return null;
    }

    @Override
    public Map<Long, String> findAllForeignEntriesById(Long clientId) {
        return clientService.findAllForeignEntriesById(clientId);
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
