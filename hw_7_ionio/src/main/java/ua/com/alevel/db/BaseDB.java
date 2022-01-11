package ua.com.alevel.db;

import ua.com.alevel.entity.BaseEntity;

import java.util.Collection;

public interface BaseDB<ENTITY extends BaseEntity, ID> {

    String getPath();

    boolean create(ENTITY entity);

    boolean update(ENTITY entity);

    boolean delete(ID id);

    ENTITY findById(ID id);

    <COLLECTION extends Collection<ENTITY>> COLLECTION findAll();
}
