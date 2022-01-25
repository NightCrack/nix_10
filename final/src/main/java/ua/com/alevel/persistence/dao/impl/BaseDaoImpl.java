package ua.com.alevel.persistence.dao.impl;

import ua.com.alevel.config.jpa.JpaConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDaoImpl {

    private final String COUNT_QUERY = "select count(*) as count from ";

    private final JpaConfig jpaConfig;

    protected BaseDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    public int count(String tableName) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(COUNT_QUERY + tableName)) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
