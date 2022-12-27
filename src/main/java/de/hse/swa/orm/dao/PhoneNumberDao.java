package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.PhoneNumber;

@ApplicationScoped
public class PhoneNumberDao {
  @Inject
  EntityManager entityManager;

  private static final Logger LOGGER = Logger.getLogger(PhoneNumberDao.class);

  public List<PhoneNumber> getAllPhoneNumbers(){
    LOGGER.debug("PhoneNumberDao.java(23): getting all PhoneNumbers of the Database");

    TypedQuery<PhoneNumber> query = entityManager.createQuery("SELECT phone FROM PhoneNumber phone", PhoneNumber.class);
    return query.getResultList();
  }

  public PhoneNumber getPhoneNumberByID(Long id){
    LOGGER.debug("PhoneNumberDao.java(30): getting a PhoneNumbers with id=" + id);

    TypedQuery<PhoneNumber> query = entityManager.createQuery("SELECT phone FROM PhoneNumber phone WHERE phone.id=:id", PhoneNumber.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public PhoneNumber save(PhoneNumber number){
    LOGGER.debug("PhoneNumberDao.java(39): Creating or Updating a PhoneNumber");

    if(number.getId() != null){
      entityManager.merge(number);
    } else {
      entityManager.persist(number);
    }

    return number;
  }

  @Transactional
  public void removePhoneNumber(Long id){
    LOGGER.debug("PhoneNumberDao.java(52): removing a PhoneNumber with id=" + id);

    TypedQuery<PhoneNumber> query = entityManager.createQuery("SELECT phone FROM PhoneNumber phone WHERE phone.id=:id", PhoneNumber.class);
    query.setParameter("id", id);
    PhoneNumber tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }

  @Transactional
  public void removeAllPhoneNumbers(){
    LOGGER.debug("PhoneNumberDao.java(52): removing all PhoneNumbers");

    try{
      entityManager.createQuery("DELETE FROM PhoneNumber WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }
}
