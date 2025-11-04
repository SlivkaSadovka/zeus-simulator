package org.zeus.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseConnectionCheckService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseConnectionCheckService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Метод для проверки подключения к базе данных
    public boolean checkDatabaseConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            // Если соединение установлено, то connection не null
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            // В случае ошибки подключения выводим сообщение
            return false;
        }
    }
}
