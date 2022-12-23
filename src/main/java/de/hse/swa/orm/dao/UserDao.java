package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.PhoneNumber;
import de.hse.swa.orm.model.User;

@ApplicationScoped
public class UserDao {
  @Inject
  EntityManager entityManager;

  private static final Logger LOGGER = Logger.getLogger(UserDao.class);

  public List<User> getAllUsers(){
    LOGGER.debug("UserDao.java(24): getting all User of the Database");

    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user", User.class);
    return query.getResultList();
  }

  public List<User> getUsersByCompany(Long id){
    LOGGER.debug("UserDao.java(31): getting all User of the Company with id=" + id);

    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user where user.companyId.id=:id", User.class)
      .setParameter("id", id);
    return query.getResultList();
  }

  public User getUserById(Long id){
    LOGGER.debug("UserDao.java(39): getting a User with id=" + id);

    return entityManager.find(User.class, id);
  }

  public User getUserByUsername(String username){
    LOGGER.debug("UserDao.java(45): getting a User by username=" + username);

    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user where user.username=:username", User.class);
    query.setParameter("username", username);
    return query.getSingleResult();
  }

  @Transactional
  public User save(User user){
    LOGGER.debug("UserDao.java(54): Creating or Updating a User");

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
    LOGGER.debug("UserDao.java(73): removing all Users of the Database");

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
    LOGGER.debug("UserDao.java(89): removing User with id=" + id);

    entityManager.remove(entityManager.find(User.class, id));
  }

  public Boolean loginUser(String username, String password){
    LOGGER.debug("UserDao.java(95): logging in a User with username=" + username);

    TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.username=:username AND user.password=:password", User.class)
      .setParameter("username", username)
      .setParameter("password", password);
    List<User> results = query.getResultList();
    return (results.size() > 0);
  }
}
