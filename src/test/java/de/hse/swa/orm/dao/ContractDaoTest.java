package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Contract;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ContractDaoTest {
  @Inject
  ContractDao _contractDao;

  private Long current_id;

  private final String default_licenseKey = "AbCdEfGhIjKlMnOpQrStUvWxYz";
  private final LocalDate default_startDate = LocalDate.of(2022, 1, 1);
  private final LocalDate default_endDate = LocalDate.of(2023, 1, 1);
  private final String default_version = "v1.0";


  private Contract createContractObject(String prefix){
    Contract contract = new Contract();
    contract.setLicenseKey(prefix + this.default_licenseKey);
    contract.setStartDate(this.default_startDate);
    contract.setEndDate(this.default_endDate);
    contract.setVersion(prefix + this.default_version);
    
    return contract;
  }  

  private Contract createContractInDatabase(String prefix){
    
    Contract contract = createContractObject(prefix);

    contract = _contractDao.save(contract);
    this.current_id = contract.getId();

    return contract;
  }

  @BeforeEach
  public void clearDatabase(){
    _contractDao.removeAllContracts();
  }

  @Test
  public void createContractTest(){
    final String prefix = "A_";

    Contract contract = createContractInDatabase(prefix);

    assertNotNull(contract.getId());
  }

  @Test
  public void getContractByIdTest(){
    final String prefix = "A_";

    createContractInDatabase(prefix);

    assertNotNull(_contractDao.getContract(this.current_id));
  }

  @Test
  public void getActiveContractsTest(){
    final String prefix = "A_";
    final int expectedActiveContractsListSize = 1;
    
    createContractInDatabase(prefix);

    assertEquals(expectedActiveContractsListSize, _contractDao.getActiveContracts().size());
  }

  @Test
  public void getAllContractTest(){
    final String[] prefixes = {"A_", "B_", "C_"};

    for(int i = 0; i < prefixes.length; i++){
      createContractInDatabase(prefixes[i]);
    }

    assertEquals(prefixes.length, _contractDao.getAllContracts().size());
  }

  @Test
  public void updateContractTest(){
    final String prefix = "A_";
    final String updatedLicenseKey = "Updated License";
    Contract contract = createContractInDatabase(prefix);

    contract.setLicenseKey(updatedLicenseKey);
    contract = _contractDao.save(contract);

    assertEquals(updatedLicenseKey, contract.getLicenseKey());
  }

  @Test
  public void deleteContractTest(){
    final String prefix = "A_";
    createContractInDatabase(prefix);

    _contractDao.removeContract(this.current_id);

    assertNull(_contractDao.getContract(this.current_id));
  }

  @Test
  public void removeAllContracts(){
    final String[] prefixes = {"A_", "B_", "C_"};

    for(int i = 0; i < prefixes.length; i++){
      createContractInDatabase(prefixes[i]);
    }

    _contractDao.removeAllContracts();
    assertEquals(0, _contractDao.getAllContracts().size());
  }
}
