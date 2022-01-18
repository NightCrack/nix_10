package ua.com.alevel.dao;

import ua.com.alevel.entity.ActionType;
import ua.com.alevel.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface DependentDao<ENTITY extends BaseEntity, FOREIGN_ENTITY extends BaseEntity> extends BaseDao<ENTITY> {

    FOREIGN_ENTITY findForeignEntityById(Long id);

    Map<Long, String> findAllForeignEntriesById(Long id);

    ActionType findEntityByParameter(String parameterName, String parameterValue);

    List<ENTITY> findAllByForeignId(Long foreignId);

    List<ENTITY> findAllBySecondForeignId(Long secondForeignId);
}
