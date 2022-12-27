package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.Ip;

@ApplicationScoped
public class IpDao {
  @Inject
  EntityManager entityManager;

  private static final Logger LOGGER = Logger.getLogger(IpDao.class);

  public List<Ip> getAllIps(){
    LOGGER.debug("IpDao.java(23): getting all IPs in the Database");

    TypedQuery<Ip> query = entityManager.createQuery("SELECT ip FROM Ip ip", Ip.class);
    return query.getResultList();
  }

  public Ip getIpByID(Long id){
    LOGGER.debug("IpDao.java(30): getting a IP with id=" + id);

    TypedQuery<Ip> query = entityManager.createQuery("SELECT ip FROM Ip ip WHERE ip.id=:id", Ip.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public Ip save(Ip ip){
    LOGGER.debug("IpDao.java(39): Creating or Updating a IP");

    if(ip.getId() != null){
      entityManager.merge(ip);
    } else {
      entityManager.persist(ip);
    }

    return ip;
  }

  @Transactional
  public void removeIp(Long id){
    LOGGER.debug("IpDao.java(52): removing Ip by id=" + id);

    TypedQuery<Ip> query = entityManager.createQuery("SELECT ip FROM Ip ip WHERE ip.id=:id", Ip.class);
    query.setParameter("id", id);
    Ip tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }

  @Transactional
  public void removeAllIps(){
    LOGGER.debug("IpDao.java(62): removing all Ips in Database");

    try{
      entityManager.createQuery("DELETE FROM Ip WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }
}
