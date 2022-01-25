package ua.com.alevel.config.jpa.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.DatasourceProperties;
import ua.com.alevel.config.jpa.JpaConfig;

import java.sql.*;

@Service
public class MySqlJpaConfig implements JpaConfig {

    private final DatasourceProperties datasourceProperties;
    private Statement statement;
    private Connection connection;

    public MySqlJpaConfig(DatasourceProperties datasourceProperties) {
        this.datasourceProperties = datasourceProperties;
    }

    @Override
    public void connect() {
        try {
            Class.forName(datasourceProperties.getDriverClassName());
            connection = DriverManager.getConnection(datasourceProperties.getUrl(), datasourceProperties.getUsername(), datasourceProperties.getPassword());
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public void deleteByCriteria(String query, Long... id) {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement(query + id[0])) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
