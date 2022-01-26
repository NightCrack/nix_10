package ua.com.alevel.persistence.dao.impl;

import ua.com.alevel.config.jpa.JpaConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDaoImpl {

    public int count(JpaConfig jpaConfig, String tableName) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select count(*) as number from " + tableName)) {
            if (resultSet.next()) {
                return resultSet.getInt("number");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public boolean existsById(JpaConfig jpaConfig, String table, String idName, Object id) {
        String appendix = idCheck(id);
        String query = "select count(*) as number from " + table + " where " + idName + " = " + appendix;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("number") == 1;
            }
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
        return false;
    }

    public void deleteByCriteria(JpaConfig jpaConfig, String table, String idName, Object id) {
        String appendix = idCheck(id);
        String query = "delete from " + table + " where " + idName + " = " + appendix;
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            System.out.println("exception = " + exception);
        }
    }

    public String idCheck(Object id) {
        String appendix = "";
        if (Number.class.isAssignableFrom(id.getClass())) {
            appendix = String.valueOf(id);
        } else {
            appendix = "'" + id + "'";
        }
        return appendix;
    }
}
