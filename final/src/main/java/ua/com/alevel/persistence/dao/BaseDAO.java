package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

public interface BaseDAO<ENTITY extends BaseEntity, ID> {

    void create(CustomResultSet<ENTITY> entity);

    void update(ENTITY entity);

    void delete(ID id);

    boolean existsById(ID id);

    ENTITY findById(ID id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);

    int count();
}
