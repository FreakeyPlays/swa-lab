package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.Address;

@ApplicationScoped
public class AddressDao {
  @Inject
  EntityManager entityManager;

	private static final Logger LOGGER = Logger.getLogger(AddressDao.class);

  public List<Address> getAllAddresses(){
    LOGGER.debug("AddressDao.java(23): getting all Addresses in the Database");

    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address", Address.class);
    return query.getResultList();
  }

  public Address getAddressByID(Long id){
    LOGGER.debug("AddressDao.java(30): getting a Address with id=" + id);

    return entityManager.find(Address.class, id);
  }

  @Transactional
  public Address save(Address address){
    LOGGER.debug("AddressDao.java(37): Creating or Updating a Address");

    if(address.getId() != null){
      entityManager.merge(address);
    } else {
      entityManager.persist(address);
    }

    return address;
  }

  @Transactional
  public void removeAddress(Long id){
    LOGGER.debug("AddressDao.java(50): removing a Address with id=" + id);

    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address WHERE address.id=:id", Address.class);
    query.setParameter("id", id);
    Address tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }

  @Transactional
  public void removeAllAddresses(){
    LOGGER.debug("AddressDao.java(60): removing all Addresses in the Database");

    try{
      entityManager.createQuery("DELETE FROM Address WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }
}
