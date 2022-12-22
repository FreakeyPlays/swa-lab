package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.orm.model.Address;

@ApplicationScoped
public class AddressDao {
  @Inject
  EntityManager entityManager;

  public List<Address> getAllAddresses(){
    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address", Address.class);
    return query.getResultList();
  }

  public Address getAddressByID(Long id){
    return entityManager.find(Address.class, id);
  }

  @Transactional
  public Address save(Address address){
    if(address.getId() != null){
      entityManager.merge(address);
    } else {
      entityManager.persist(address);
    }

    return address;
  }

  @Transactional
  public void removeAddress(Long id){
    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address WHERE address.id=:id", Address.class);
    query.setParameter("id", id);
    Address tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }

  @Transactional
  public void removeAllAddresses(){
    try{
      entityManager.createQuery("DELETE FROM Address WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }
}
