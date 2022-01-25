package ua.com.alevel.service;

import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.Collection;

public interface BaseService<ENTITY extends BaseEntity, ID> {

    void create(CustomResultSet<ENTITY> entity);

    void update(ENTITY entity);

    void delete(ID id);

    ENTITY findById(ID id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);
}
