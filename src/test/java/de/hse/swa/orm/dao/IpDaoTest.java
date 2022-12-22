package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Ip;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class IpDaoTest {
  @Inject
  IpDao _ipDao;

  private Long current_id;

  private final String default_address = "255.255.255.255";

  private Ip createIpObject(String prefix){
    Ip ip = new Ip();
    ip.setAddress(prefix + this.default_address);
    
    return ip;
  }  

  private Ip createIpInDatabase(String prefix){
    Ip ip = createIpObject(prefix);

    _ipDao.save(ip);
    this.current_id = ip.getId();

    return ip;
  }

  @BeforeEach
  public void clearDatabase(){
    _ipDao.removeAllIps();
  }

  @Test
  public void createIpTest(){
    final String prefix = "A_";

    Ip ip = createIpInDatabase(prefix);

    assertEquals(prefix + this.default_address, ip.getAddress());
  }

  @Test
  public void getIpByIdTest(){
    final String prefix = "A_";

    createIpInDatabase(prefix);

    assertNotNull(_ipDao.getIpByID(this.current_id));
  }

  @Test
  public void getAllIpTest(){
    final String[] prefix = {"A_", "B_", "C_"};

    for(int i = 0; i < prefix.length; i++){
      createIpInDatabase(prefix[i]);
    }

    assertEquals(prefix.length, _ipDao.getAllIps().size());
  }

  @Test
  public void updateIpTest(){
    final String prefix = "A_";
    final String updatedAddress = "0.0.0.0";
    Ip ip = createIpInDatabase(prefix);
    
    ip.setAddress(updatedAddress);
    ip = _ipDao.save(ip);

    assertEquals(updatedAddress, ip.getAddress());
  }

  @Test
  public void deleteIpTest(){
    final String prefix = "A_";
    final int expectedIpListSize = 0;
    createIpInDatabase(prefix);

    _ipDao.removeIp(this.current_id);

    assertEquals(expectedIpListSize, _ipDao.getAllIps().size());
  }
}
