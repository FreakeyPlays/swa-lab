package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Address;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AddressDaoTest {
  @Inject
  AddressDao _addressDao;

  private Long current_id;

  final String default_country = "Germany";
  final String default_area = "Baden-Wuettemberg";
  final String default_city = "Esslingen";
  final int default_zipCode = 12345;
  final String default_streetName = "Flandernstrasse";
  final int default_houseNumber = 101;

  private Address createAddressObject(String prefix){
    Address address = new Address();
    address.setCountry(prefix + this.default_country);
    address.setArea(prefix + this.default_area);
    address.setCity(prefix + this.default_city);
    address.setZipCode(this.default_zipCode);
    address.setStreetName(prefix + this.default_streetName);
    address.setHouseNumber(this.default_houseNumber);

    return address;
  }  

  private Address createAddressInDatabase(String prefix){
    Address address = createAddressObject(prefix);

    address = _addressDao.save(address);
    this.current_id = address.getId();

    return address;
  }

  @BeforeEach
  public void clearDatabase(){
    _addressDao.removeAllAddresses();
  }

  @Test
  public void createAddressTest(){
    final String prefix = "A_";

    Address address = createAddressInDatabase(prefix);

    assertEquals(prefix + this.default_country, address.getCountry());
  }

  @Test
  public void getAddressByIdTest(){
    final String prefix = "A_";

    createAddressInDatabase(prefix);

    assertNotNull(_addressDao.getAddressByID(current_id));
  }

  @Test
  public void getAllAddressesTest(){
    final String[] prefix = {"A_", "B_", "C_"};

    for(int i = 0; i < prefix.length; i++){
      createAddressInDatabase(prefix[i]);
    }

    assertEquals(prefix.length, _addressDao.getAllAddresses().size());
  }

  @Test
  public void updateAddressTest(){
    final String prefix = "A_";
    final String updatedCountry = "Updated Country";
    Address address = createAddressInDatabase(prefix);

    address.setCountry(updatedCountry);
    address = _addressDao.save(address);

    assertEquals(updatedCountry, address.getCountry());
  }

  @Test
  public void deleteAddressTest(){
    final String prefix = "A_";
    final int expectedAddressListSize = 0;
    createAddressInDatabase(prefix);

    _addressDao.removeAddress(this.current_id);

    assertNull(_addressDao.getAddressByID(this.current_id));
  }
}
