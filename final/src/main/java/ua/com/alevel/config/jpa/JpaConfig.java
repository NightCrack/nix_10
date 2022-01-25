package ua.com.alevel.config.jpa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface JpaConfig {

    void connect();

    Connection getConnection();

    Statement getStatement();

    void deleteByCriteria(String query, Long... id);
}
