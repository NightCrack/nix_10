package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

public interface DependentService<ENTITY extends BaseEntity, ID, FOREIGN_ID> extends BaseService<ENTITY, ID> {

    DataTableResponse<ENTITY> findAllByForeignId(DataTableRequest request, FOREIGN_ID foreignId);
}
