package com.data.poc.repository;

import com.data.poc.model.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SqliteRepository implements Repository {

    private final SqliteConnection connection;

    public SqliteRepository(SqliteConnection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable(String tableName) {
        if (tableExists(tableName)) {
            dropTable(tableName);
        }

        String createTableQuery = String.format("CREATE TABLE %s (id text, firstName text, lastName text)", tableName);
        Connection connection = this.connection.getConnection();

        try {
            Statement statement = connection.createStatement();
            log.info("Executing query: {}", createTableQuery);
            statement.executeUpdate(createTableQuery);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Failed to create table");
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable(String tableName) {
        String dropTableQuery = String.format("DROP TABLE %s", tableName);
        Connection connection = this.connection.getConnection();

        try {
            Statement statement = connection.createStatement();
            log.info("Executing query: {}", dropTableQuery);
            statement.executeUpdate(dropTableQuery);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String tableName, User user) {
        String insertQuery = String.format("INSERT INTO %s (id, firstName, lastName) VALUES ('%s', '%s', '%s')",
                tableName, user.getId(), user.getFirstName(), user.getLastName());
        Connection connection = this.connection.getConnection();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            log.info("Executing query: {}", insertQuery);
            statement.executeUpdate(insertQuery);
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            log.error("Failed to insert data");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> selectAll(String tableName) {
        String selectQuery = String.format("SELECT * FROM %s", tableName);
        Connection connection = this.connection.getConnection();
        List<User> userList = new ArrayList<>();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectQuery);

            while (result.next()) {
                User user = new User();
                user.setId(result.getString("id"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                userList.add(user);
            }
            result.close();

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User selectOne(String tableName, String firstName, String lastName) {
        String selectQuery = String.format("SELECT * FROM %s WHERE firstName = '%s' and lastName = '%s'", tableName, firstName, lastName);
        Connection connection = this.connection.getConnection();
        User user = new User();
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectQuery);

            while (result.next()) {
                user.setId(result.getString("id"));
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
            }
            result.close();

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean tableExists(String tableName) {
        try (Connection connection = this.connection.getConnection()) {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            return tables.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void update(String tableName, User user) {
        String updateQuery = String.format("UPDATE %s SET firstName = '%s', lastName = '%s' WHERE id = '%s'",
                tableName, user.getFirstName(), user.getLastName(), user.getId());
        Connection connection = this.connection.getConnection();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            log.info("Executing query: {}", updateQuery);
            statement.executeUpdate(updateQuery);
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            log.error("Failed to update data");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByName(String tableName, String firstName, String lastName) {
        String deleteQuery = String.format("DELETE FROM %s WHERE firstName = '%s' and lastName = '%s'",
                tableName, firstName, lastName);
        Connection connection = this.connection.getConnection();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            log.info("Executing query: {}", deleteQuery);
            statement.executeUpdate(deleteQuery);
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            log.error("Failed to delete data");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String tableName, String id) {
        String deleteQuery = String.format("DELETE FROM %s WHERE id = '%s'", tableName, id);
        Connection connection = this.connection.getConnection();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            log.info("Executing query: {}", deleteQuery);
            statement.executeUpdate(deleteQuery);
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            log.error("Failed to delete data");
            e.printStackTrace();
        }
    }
}
