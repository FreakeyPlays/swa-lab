package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.orm.model.PhoneNumber;

@ApplicationScoped
public class PhoneNumberDao {
  @Inject
  EntityManager entityManager;

  @Transactional
  public PhoneNumber addPhoneNumber(PhoneNumber phoneNumber){
    entityManager.persist(phoneNumber);
    return phoneNumber;
  }

  public List<PhoneNumber> getAllPhoneNumbers(){
    TypedQuery<PhoneNumber> query = entityManager.createQuery("SELECT phone FROM PhoneNumber phone", PhoneNumber.class);
    return query.getResultList();
  }

  public PhoneNumber getPhoneNumberByID(Long id){
    TypedQuery<PhoneNumber> query = entityManager.createQuery("SELECT phone FROM PhoneNumber phone WHERE phone.id=:id", PhoneNumber.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber){
    entityManager.merge(phoneNumber);
    return phoneNumber;
  }

  @Transactional
  public void deletePhoneNumber(Long id){
    TypedQuery<PhoneNumber> query = entityManager.createQuery("SELECT phone FROM PhoneNumber phone WHERE phone.id=:id", PhoneNumber.class);
    query.setParameter("id", id);
    PhoneNumber tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }
}
