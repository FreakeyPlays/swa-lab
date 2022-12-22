package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.orm.model.Ip;

@ApplicationScoped
public class IpDao {
  @Inject
  EntityManager entityManager;

  public List<Ip> getAllIps(){
    TypedQuery<Ip> query = entityManager.createQuery("SELECT ip FROM Ip ip", Ip.class);
    return query.getResultList();
  }

  public Ip getIpByID(Long id){
    TypedQuery<Ip> query = entityManager.createQuery("SELECT ip FROM Ip ip WHERE ip.id=:id", Ip.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public Ip save(Ip ip){
    if(ip.getId() != null){
      entityManager.merge(ip);
    } else {
      entityManager.persist(ip);
    }

    return ip;
  }

  @Transactional
  public void removeIp(Long id){
    TypedQuery<Ip> query = entityManager.createQuery("SELECT ip FROM Ip ip WHERE ip.id=:id", Ip.class);
    query.setParameter("id", id);
    Ip tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }

  @Transactional
  public void removeAllIps(){
    try{
      entityManager.createQuery("DELETE FROM Ip WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }
}
