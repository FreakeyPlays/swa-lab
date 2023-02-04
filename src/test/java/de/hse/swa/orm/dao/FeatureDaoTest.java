package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Feature;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FeatureDaoTest {
  @Inject
  FeatureDao _featureDao;

  private Long current_id;

  private Feature createFeatureObject(int number){
    Feature feature = new Feature();
    feature.setNumber(number);
    
    return feature;
  }  

  private Feature createFeatureInDatabase(int number){
    Feature feature = createFeatureObject(number);

    feature = _featureDao.save(feature);
    this.current_id = feature.getId();

    return feature;
  }

  @BeforeEach
  public void clearDatabase(){
    _featureDao.removeAllFeatures();
  }

  @Test
  public void createFeatureTest(){
    final int number = 101;

    Feature feature = createFeatureInDatabase(number);

    assertEquals(number, feature.getNumber());
  }

  @Test
  public void getFeatureByIdTest(){
    final int number = 102;

    createFeatureInDatabase(number);

    assertNotNull(_featureDao.getFeatureByID(this.current_id));
  }

  @Test
  public void getAllFeaturesTest(){
    final int[] numbers = {113, 123, 133};

    for(int i = 0; i < numbers.length; i++){
      createFeatureInDatabase(numbers[i]);
    }

    assertEquals(numbers.length, _featureDao.getAllFeatures().size());
  }

  @Test
  public void updateFeatureTest(){
    final int number = 104;
    final int updatedNumber = 140;
    Feature feature = createFeatureInDatabase(number);
    
    feature.setNumber(updatedNumber);
    feature = _featureDao.save(feature);

    assertEquals(updatedNumber, feature.getNumber());
  }

  @Test
  public void deleteFeatureTest(){
    final int number = 105;
    final int expectedFeatureListSize = 0;
    createFeatureInDatabase(number);

    _featureDao.removeFeature(this.current_id);

    assertEquals(expectedFeatureListSize, _featureDao.getAllFeatures().size());
  }

  @Test
  public void removeAllFeaturesTest(){
    final int[] numbers = {113, 123, 133};

    for(int i = 0; i < numbers.length; i++){
      createFeatureInDatabase(numbers[i]);
    }

    _featureDao.removeAllFeatures();
    assertEquals(0, _featureDao.getAllFeatures().size());
  }
}
