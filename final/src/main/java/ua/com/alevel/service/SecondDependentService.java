package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface SecondDependentService<ENTITY extends BaseEntity, ID, FOREIGN_ID, SECOND_FOREIGN_KEY> extends DependentService<ENTITY, ID, FOREIGN_ID> {

    void deleteAllBySecondForeignId(SECOND_FOREIGN_KEY secondForeignKey);

    List<ENTITY> findAllBySecondForeignId(SECOND_FOREIGN_KEY secondForeignKey);
}
