package com.example.contactmgr.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.contactmgr.dao.UserDao;
import com.example.contactmgr.model.User;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    
    @Override
    public List<User> findAll() {
	return userDao.findAll();
    }

    @Override
    public void save(User user, MultipartFile file) {
	try {
	    user.setProfilePicture(file.getBytes());
	    userDao.save(user);
	}catch(IOException e) {
	    System.err.println("Unable to get bytes from uploaded file");
	}
	
	
    }

    @Override
    public void delete(User user) {
	userDao.delete(user);
	
    }

    @Override
    public User findById(Long id) {
	return userDao.findById(id);
    }
    
    @Override
    public  List<User> findByFirstName(String firstName) {
	return userDao.findByFirstName(firstName);
    }

}
