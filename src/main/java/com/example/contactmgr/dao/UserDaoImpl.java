package com.example.contactmgr.dao;


import com.example.contactmgr.model.User;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<User> findAll() {
		//Open a Session
		Session session = sessionFactory.openSession();
		
		//Get a Criteria Builder
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		//Create a CriteriaQuery
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot);
		
		TypedQuery<User> q = session.createQuery(criteria);
		
		//User Criteria to query with session to fetch all Users data
		List<User> users = q.getResultList();
		
		//Close a session
		session.close();
		
		return users;
	}
	
	@Override
	public List<User> findByFirstName(String firstName) {
	    
//	    firstName.toUpperCase();
//	    
//	    String pattern = firstName + "%";
	    	    
	    Session session = sessionFactory.openSession();
	    
	    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
	    
	    CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
	    
	    Root<User> userRoot = criteria.from(User.class);
	    
//	    Predicate predicate = criteriaBuilder.like(userRoot.get(User).as(String.class), firstName);
	    
//	    Predicate predicate = criteriaBuilder.like(userRoot.<String>get("firstName"), pattern);
	   
	    Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(userRoot.<String>get("firstName")), 
		    "%" + firstName.toUpperCase() + "%");
	    
	    criteria.where(predicate);
	
	    TypedQuery<User> q = session.createQuery(criteria);
	    
	    List<User> users = q.getResultList();
	    
	    session.close();
	    
	    return users;
	}

	@Override
	public User findById(Long id) {
		//Open a Session
		Session session = sessionFactory.openSession();
		
		//Get User Object according to its ID
		User user = session.get(User.class, id);
		
		//Close a session
		session.close();
		
		return user;
		
	}

	@Override
	public void save(User theUser) {
		//Open a Session
		Session session = sessionFactory.openSession();
				
		//Begin a Transcation
		session.beginTransaction();
				
		//Save the User data
		session.saveOrUpdate(theUser);
				
		//Commit the transcation
		session.getTransaction().commit();
				
		//Close the session
		session.close();
		
	}

	@Override
	public void delete(User theUser) {
		//Open a Session
		Session session = sessionFactory.openSession();
		
		//Begin a Transcation
		session.beginTransaction();
		
		//Delete the User data
		session.delete(theUser);
		
		//Commit the transcation
		session.getTransaction().commit();
		
		//Close the session
		session.close();
		
	}
	

}
