package ua.com.alevel.service;

import ua.com.alevel.entity.BaseEntity;

import java.util.Collection;

public interface BaseService<ENTITY extends BaseEntity, ID> {

    boolean create(ENTITY entity);

    boolean update(ENTITY entity);

    boolean delete(ID id);

    ENTITY findById(ID id);

    <COLLECTION extends Collection<ENTITY>> COLLECTION findAll();
}
