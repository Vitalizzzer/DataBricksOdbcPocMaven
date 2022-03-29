package com.data.poc.service;

import com.data.poc.model.User;
import com.data.poc.repository.Repository;

import java.util.List;

public class DataService {
    private final Repository repository;

    public DataService(Repository repository) {
        this.repository = repository;
    }

    public void insertData(String tableName, User user) {
        repository.insert(tableName, user);
    }

    public void createDataTable(String tableName) {
        repository.createTable(tableName);
    }

    public User selectSingleUser(String tableName, String firstName, String lastName) {
        return repository.selectOne(tableName, firstName, lastName);
    }

    public List<User> selectAllUsers(String tableName) {
        return repository.selectAll(tableName);
    }

    public void updateUser(String tableName, User user) {
        repository.update(tableName, user);
    }

    public void deleteDataByName(String tableName, String firstName, String lastName) {
        repository.deleteByName(tableName, firstName, lastName);
    }

    public void deleteDataById(String tableName, String id){
        repository.deleteById(tableName, id);
    }

}
