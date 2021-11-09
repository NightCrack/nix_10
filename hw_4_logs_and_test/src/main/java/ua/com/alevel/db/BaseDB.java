package ua.com.alevel.db;

import ua.com.alevel.entity.BaseEntity;


public interface BaseDB<ENTITY extends BaseEntity, ENTITY_TWO> {

    void create(ENTITY entity);

    boolean update(ENTITY entity);

    boolean delete(ENTITY_TWO id);

    ENTITY findById(ENTITY_TWO id);

    ENTITY[] findAll();
}
