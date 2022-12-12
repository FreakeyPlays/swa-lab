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

  @Transactional
  public Address addAddress(Address address){
    entityManager.persist(address);
    return address;
  }

  public List<Address> getAllAddresses(){
    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address", Address.class);
    return query.getResultList();
  }

  public Address getAddressByID(Long id){
    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address WHERE address.id=:id", Address.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public Address updateAddress(Address address){
    entityManager.merge(address);
    return address;
  }

  @Transactional
  public void deleteAddress(Long id){
    TypedQuery<Address> query = entityManager.createQuery("SELECT address FROM Address address WHERE address.id=:id", Address.class);
    query.setParameter("id", id);
    Address tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }
}
