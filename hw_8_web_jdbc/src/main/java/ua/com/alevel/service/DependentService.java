package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface DependentService<ENTITY extends BaseEntity, ID, FOREIGN_ID> extends BaseService<ENTITY, ID> {

    void deleteAllByForeignId(FOREIGN_ID foreignId);

    List<ENTITY> findAllByForeignId(FOREIGN_ID foreignId);
}
