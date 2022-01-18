package ua.com.alevel.facade;

import ua.com.alevel.dto.request.ClientRequestDto;
import ua.com.alevel.dto.response.ClientResponseDto;
import ua.com.alevel.entity.BaseEntity;

public interface ClientFacade extends DependentFacade<ClientRequestDto, ClientResponseDto, BaseEntity> {
}
