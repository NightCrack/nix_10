package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.util.CustomResultSet;

public interface BaseService<ENTITY extends BaseEntity, ID> {

    void create(CustomResultSet<ENTITY> entity);

    void update(ENTITY entity);

    void delete(ID id);

    ENTITY findById(ID id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);
}
