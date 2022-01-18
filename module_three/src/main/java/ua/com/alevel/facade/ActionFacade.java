package ua.com.alevel.facade;

import ua.com.alevel.dto.request.ActionRequestDto;
import ua.com.alevel.dto.response.ActionResponseDto;
import ua.com.alevel.entity.BaseEntity;

public interface ActionFacade extends DependentFacade<ActionRequestDto, ActionResponseDto, BaseEntity> {
}
