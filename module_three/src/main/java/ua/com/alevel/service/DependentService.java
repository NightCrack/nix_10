package ua.com.alevel.service;

import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Map;

public interface DependentService<ENTITY extends BaseEntity, FOREIGN_ENTITY extends BaseEntity> extends BaseService<ENTITY> {

    FOREIGN_ENTITY findForeignEntityById(Long id);

    Map<Long, String> findAllForeignEntriesById(Long id);

    ActionType findEntityByParameter(String parameterName, String parameterValue);

    void balanceChange(BigDecimal amount, Long actionId, Boolean deposit);

    String writeOutByForeignId(Long foreignId);

    String writeOutBySecondForeignId(Long secondForeignId);
}
