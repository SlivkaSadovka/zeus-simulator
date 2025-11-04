package org.zeus.demo.controller;

import org.zeus.demo.service.DatabaseConnectionCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseCheckController {

    private final DatabaseConnectionCheckService databaseConnectionCheckService;

    @Autowired
    public DatabaseCheckController(DatabaseConnectionCheckService databaseConnectionCheckService) {
        this.databaseConnectionCheckService = databaseConnectionCheckService;
    }

    @GetMapping("/check-db-connection")
    public String checkDatabaseConnection() {
        boolean isConnected = databaseConnectionCheckService.checkDatabaseConnection();
        if (isConnected) {
            return "Database is connected!";
        } else {
            return "Database connection failed!";
        }
    }
}
