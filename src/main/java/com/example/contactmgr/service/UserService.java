package com.example.contactmgr.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.contactmgr.model.User;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    void save(User user, MultipartFile file);
    void delete(User user);
    List<User> findByFirstName(String firstName);
}
