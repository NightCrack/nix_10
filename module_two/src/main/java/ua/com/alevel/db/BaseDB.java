package ua.com.alevel.db;

import ua.com.alevel.entity.BaseEntity;

import java.util.Collection;

public interface BaseDB<ENTITY extends BaseEntity, CLASS> {

    boolean create(ENTITY entity);

    boolean update(ENTITY entity);

    boolean delete(CLASS entity);

    ENTITY find(CLASS entity);

    <COLLECTION extends Collection<ENTITY>> COLLECTION find();
}
