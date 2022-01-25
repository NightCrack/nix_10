package ua.com.alevel.persistence.dao.impl;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public class CustomResultSet<ENTITY extends BaseEntity> {

    private List<?> params;
    private ENTITY entity;

    public CustomResultSet(ENTITY entity, List<?> params) {
        this.params = params;
        this.entity = entity;
    }

    public List<?> getParams() {
        return params;
    }

    public ENTITY getEntity() {
        return entity;
    }
}
