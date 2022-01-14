package ua.com.alevel.service;

import ua.com.alevel.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseService<ENTITY extends BaseEntity, ID> {

    void create(ENTITY entity);

    void update(ENTITY entity);

    void delete(ID id);

    Optional<ENTITY> findById(ID id);

    List<ENTITY> findAll();

}
