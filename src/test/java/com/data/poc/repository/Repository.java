package com.data.poc.repository;

import com.data.poc.model.User;

import java.util.List;

public interface Repository {

    void createTable(String name);
    void dropTable(String name);
    void insert(String tableName, User user);
    List<User> selectAll(String tableName);
    User selectOne(String tableName, String firstName, String lastName);
    boolean tableExists(String tableName);
    void update(String tableName, User user);
    void deleteByName(String tableName, String firstName, String lastName);
    void deleteById(String tableName, String id);
}
