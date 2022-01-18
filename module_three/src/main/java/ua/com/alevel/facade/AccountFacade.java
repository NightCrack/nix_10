package ua.com.alevel.facade;

import ua.com.alevel.dto.request.AccountRequestDto;
import ua.com.alevel.dto.response.AccountResponseDto;
import ua.com.alevel.entity.Client;

public interface AccountFacade extends DependentFacade<AccountRequestDto, AccountResponseDto, Client> {
}
