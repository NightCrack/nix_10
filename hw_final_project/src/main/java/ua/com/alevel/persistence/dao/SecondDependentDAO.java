package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

public interface SecondDependentDAO<ENTITY extends BaseEntity, ID, FOREIGN_ID, SECOND_FOREIGN_ID> extends DependentDAO<ENTITY, ID, FOREIGN_ID> {

    DataTableResponse<ENTITY> findAllBySecondForeignId(DataTableRequest request, SECOND_FOREIGN_ID secondForeignId);

    int secondForeignCount(SECOND_FOREIGN_ID secondForeignId);
}
