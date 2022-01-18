package ua.com.alevel.facade;

import ua.com.alevel.dto.RequestDto;
import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;

import java.util.Map;

public interface DependentFacade<REQ extends RequestDto, RES extends ResponseDto, FOREIGN_ENTITY extends BaseEntity> extends BaseFacade<REQ, RES> {

    FOREIGN_ENTITY findForeignEntityById(Long id);

    Map<Long, String> findAllForeignEntriesById(Long id);

    ActionType findEntityByParameter(String parameterName, String parameterValue);

    String writeOutByForeignId(Long foreignId);

    String writeOutBySecondForeignId(Long secondForeignId);
}
