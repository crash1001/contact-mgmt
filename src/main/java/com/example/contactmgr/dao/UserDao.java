package com.example.contactmgr.dao;

import java.util.List;

import com.example.contactmgr.model.User;

public interface UserDao {
    List<User> findAll();
    List<User> findByFirstName(String firstName);
    void save(User user);
    void delete(User user);
    User findById(Long id);
}
