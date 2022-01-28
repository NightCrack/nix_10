package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface SecondDependentService<ENTITY extends BaseEntity, ID, FOREIGN_ID, SECOND_FOREIGN_KEY> extends DependentService<ENTITY, ID, FOREIGN_ID> {

    DataTableResponse<ENTITY> findAllBySecondForeignId(DataTableRequest request, SECOND_FOREIGN_KEY secondForeignKey);
}
