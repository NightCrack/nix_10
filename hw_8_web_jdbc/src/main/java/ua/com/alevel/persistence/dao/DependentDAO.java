package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface DependentDAO<ENTITY extends BaseEntity, ID, FOREIGN_ID> extends BaseDAO<ENTITY, ID> {

    DataTableResponse<ENTITY> findAllByForeignId(DataTableRequest request, FOREIGN_ID foreignId);

    int foreignCount(FOREIGN_ID foreignId);
}
