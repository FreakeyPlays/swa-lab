package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.PhoneNumber;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PhoneNumberDaoTest {
  @Inject
  PhoneNumberDao _phoneNumberDao;

  private Long current_id;

  private final String default_number = "0123456789";

  private PhoneNumber createPhoneNumberObject(String prefix){
    PhoneNumber number = new PhoneNumber();
    number.setNumber(prefix + this.default_number);
    
    return number;
  }  

  private PhoneNumber createPhoneNumberInDatabase(String prefix){
    PhoneNumber number = createPhoneNumberObject(prefix);

    number = _phoneNumberDao.save(number);
    this.current_id = number.getId();

    return number;
  }

  @BeforeEach
  public void clearDatabase(){
    _phoneNumberDao.removeAllPhoneNumbers();
  }

  @Test
  public void createPhoneNumberTest(){
    final String prefix = "A_";

    PhoneNumber number = createPhoneNumberInDatabase(prefix);

    assertEquals(prefix + this.default_number, number.getNumber());
  }

  @Test
  public void getPhoneNumberByIdTest(){
    final String prefix = "A_";

    createPhoneNumberInDatabase(prefix);

    assertNotNull(_phoneNumberDao.getPhoneNumberByID(this.current_id));
  }

  @Test
  public void getAllPhoneNumbersTest(){
    final String[] prefix = {"A_", "B_", "C_"};

    for(int i = 0; i < prefix.length; i++){
      createPhoneNumberInDatabase(prefix[i]);
    }

    assertEquals(prefix.length, _phoneNumberDao.getAllPhoneNumbers().size());
  }

  @Test
  public void updateFeatureTest(){
    final String prefix = "A_";
    final String updatedNumber = "B_" + this.default_number;
    PhoneNumber number = createPhoneNumberInDatabase(prefix);
    
    number.setNumber(updatedNumber);
    number = _phoneNumberDao.save(number);

    assertEquals(updatedNumber, number.getNumber());
  }

  @Test
  public void deleteFeatureTest(){
    final String prefix = "A_";
    final int expectedFeatureListSize = 0;
    createPhoneNumberInDatabase(prefix);

    _phoneNumberDao.removePhoneNumber(this.current_id);

    assertEquals(expectedFeatureListSize, _phoneNumberDao.getAllPhoneNumbers().size());
  }

  @Test
  public void removeAllPhoneNumbersTest(){
    final String[] prefix = {"A_", "B_", "C_"};

    for(int i = 0; i < prefix.length; i++){
      createPhoneNumberInDatabase(prefix[i]);
    }

    _phoneNumberDao.removeAllPhoneNumbers();
    assertEquals(0, _phoneNumberDao.getAllPhoneNumbers().size());
  }
}
