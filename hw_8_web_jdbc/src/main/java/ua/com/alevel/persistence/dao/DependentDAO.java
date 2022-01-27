package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface DependentDAO<ENTITY extends BaseEntity, ID, FOREIGN_ID> extends BaseDAO<ENTITY, ID> {

    void deleteAllByForeignId(FOREIGN_ID foreignId);

    List<ENTITY> findAllByForeignId(FOREIGN_ID foreignId);
}
