package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.Feature;

@ApplicationScoped
public class FeatureDao {
  @Inject
  EntityManager entityManager;

  private static final Logger LOGGER = Logger.getLogger(FeatureDao.class);

  public List<Feature> getAllFeatures(){
    LOGGER.debug("FeatureDao.java(23): getting all Features in the Database");
    
    TypedQuery<Feature> query = entityManager.createQuery("SELECT feature FROM Feature feature", Feature.class);
    return query.getResultList();
  }

  public Feature getFeatureByID(Long id){
    LOGGER.debug("FeatureDao.java(30): getting a Feature with id=" + id);

    TypedQuery<Feature> query = entityManager.createQuery("SELECT feature FROM Feature feature WHERE feature.id=:id", Feature.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public Feature save(Feature feature){
    LOGGER.debug("FeatureDao.java(39): Creating or Updating a Feature");

    if(feature.getId() != null){
      entityManager.merge(feature);
    } else {
      entityManager.persist(feature);
    }

    return feature;
  }

  @Transactional
  public void removeFeature(Long id){
    LOGGER.debug("FeatureDao.java(52): removing a Feature by id=" + id);

    TypedQuery<Feature> query = entityManager.createQuery("SELECT feature FROM Feature feature WHERE feature.id=:id", Feature.class);
    query.setParameter("id", id);
    Feature tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }

  @Transactional
  public void removeAllFeatures(){
    LOGGER.debug("FeatureDao.java(62): removing all Features in the Database");

    try{
      entityManager.createQuery("DELETE FROM Feature WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }
}
