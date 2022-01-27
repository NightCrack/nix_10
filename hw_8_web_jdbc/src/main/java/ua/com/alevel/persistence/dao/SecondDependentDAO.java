package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface SecondDependentDAO<ENTITY extends BaseEntity, ID, FOREIGN_ID, SECOND_FOREIGN_KEY> extends DependentDAO<ENTITY, ID, FOREIGN_ID> {

    void deleteAllBySecondForeignId(SECOND_FOREIGN_KEY secondForeignKey);

    List<ENTITY> findAllBySecondForeignId(SECOND_FOREIGN_KEY secondForeignKey);
}
