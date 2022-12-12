package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.hse.swa.orm.model.Feature;

@ApplicationScoped
public class FeatureDao {
  @Inject
  EntityManager entityManager;

  @Transactional
  public Feature addFeature(Feature feature){
    entityManager.persist(feature);
    return feature;
  }

  public List<Feature> getAllFeatures(){
    TypedQuery<Feature> query = entityManager.createQuery("SELECT feature FROM Feature feature", Feature.class);
    return query.getResultList();
  }

  public Feature getFeatureByID(Long id){
    TypedQuery<Feature> query = entityManager.createQuery("SELECT feature FROM Feature feature WHERE feature.id=:id", Feature.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  @Transactional
  public Feature updateFeature(Feature feature){
    entityManager.merge(feature);
    return feature;
  }

  @Transactional
  public void deleteFeature(Long id){
    TypedQuery<Feature> query = entityManager.createQuery("SELECT feature FROM Feature feature WHERE feature.id=:id", Feature.class);
    query.setParameter("id", id);
    Feature tempAddress = query.getSingleResult();
    entityManager.remove(entityManager.contains(tempAddress) ? tempAddress : entityManager.merge(tempAddress));
  }
}
