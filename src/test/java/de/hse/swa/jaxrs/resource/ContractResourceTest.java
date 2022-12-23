package de.hse.swa.jaxrs.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Contract;
import de.hse.swa.orm.dao.CompanyDao;
import de.hse.swa.orm.model.Address;
import de.hse.swa.orm.model.Company;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ContractResourceTest {
  @Inject
  CompanyDao _companyDao;

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
    contract.setVersion(prefix + this.default_licenseKey);

    Address address = new Address();
    Company company = new Company();
    company.setAddress(address);
    company = _companyDao.save(company);

    contract.setCompanyId(company);
    
    return contract;
  }

  private Contract createContractDatabaseEntry(String prefix){
    Contract contract = createContractObject(prefix);

    Contract createdContract = 
    given()
      .log().all()
      .contentType("application/json")
      .body(contract)
    .when()
      .post("/contract/create")
    .then()
      .statusCode(200)
    .extract()
      .body()
        .as(Contract.class);

    this.current_id = createdContract.getId();

    return createdContract;
  }

  @BeforeEach
  public void cleanupDatabase(){
    given()
    .when()
      .delete("/contract/remove/all");
  }

  @Test
  public void createContractTest(){
    final String prefix = "A_";
    Contract contract = createContractObject(prefix);

    given()
      .contentType("application/json")
      .body(contract)
    .when()
      .post("/contract/create")
    .then()
      .statusCode(200)
      .body("licenseKey", equalTo(prefix + this.default_licenseKey));
  }

  @Test
  public void getAllContractsTest(){
    final String[] prefixes = {"A_", "B_", "C_"};
    for(int i = 0; i < prefixes.length; i++){
      createContractDatabaseEntry(prefixes[i]);
    }
    
    given()
      .contentType("application/json")  
    .when()
      .get("/contract/all")
    .then()
      .statusCode(200)
      .body("$.size", equalTo(prefixes.length));
  }

  @Test
  public void getContractByIdTest(){
    final String prefix = "A_";
    Contract contract = createContractDatabaseEntry(prefix);

    given()
      .contentType("application/json")
      .pathParam("id", this.current_id)
    .when()
      .get("/contract/{id}")
    .then()
      .statusCode(200)
      .body("$", hasKey("id"));
  }

  @Test
  public void getAllActiveContractsTest(){
    final String prefix = "A_";
    Contract contract = createContractDatabaseEntry(prefix);

    given()
      .contentType("application/json")
    .when()
      .get("/contract/all/active")
    .then()
      .statusCode(200)
      .body("$.size", equalTo(1));
  }

  @Test
  public void updateContractTest(){
    final String prefix = "A_";
    final String updatedLicenseKey = "Updated Key";
    Contract contract = createContractDatabaseEntry(prefix);

    contract.setLicenseKey(updatedLicenseKey);

    given()
      .contentType("application/json")
      .body(contract)
    .when()
      .put("/contract/update")
    .then()
      .statusCode(200)
      .body("licenseKey", equalTo(updatedLicenseKey));
  }

  @Test
  public void deleteContractTest(){
    final String prefix = "A_";
    Contract contract = createContractDatabaseEntry(prefix);

    given()
      .contentType("application/json")
      .pathParam("id", this.current_id)
    .when()
      .delete("/contract/remove/{id}")
    .then()
      .statusCode(204);
  }
}
