package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.orm.model.PhoneNumber;
import de.hse.swa.orm.model.User;

@ApplicationScoped
public class UserDao {
  @Inject
  EntityManager entityManager;

  public List<User> getAllUsers(){
    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user", User.class);
    return query.getResultList();
  }

  public List<User> getUsersByCompany(Long id){
    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user where user.companyId.id=:id", User.class)
      .setParameter("id", id);
    return query.getResultList();
  }

  public User getUserById(Long id){
    return entityManager.find(User.class, id);
  }

  public User getUserByUsername(String username){
    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user where user.username=:username", User.class);
    query.setParameter("username", username);
    return query.getSingleResult();
  }

  @Transactional
  public User save(User user){
    if(user.getPhoneNumbers() != null){
      for(PhoneNumber number : user.getPhoneNumbers()){
        number.setUser(user);
      }
    }

    if(user.getId() != null){
      entityManager.merge(user);
    } else {
      entityManager.persist(user);
    }

    return user;
  }

  @Transactional
  public void removeAllUsers(){
    try{
      entityManager.createQuery("DELETE FROM PhoneNumber WHERE id >= 0")
        .executeUpdate();
      entityManager.createQuery("DELETE FROM User WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }

  @Transactional
  public void removeUser(Long id){
    entityManager.remove(entityManager.find(User.class, id));
  }

  public Boolean loginUser(String username, String password){
    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.username=:username AND user.password=:password", User.class)
      .setParameter("username", username)
      .setParameter("password", password);
    List<User> results = query.getResultList();
    return (results.size() > 0);
  }
}
