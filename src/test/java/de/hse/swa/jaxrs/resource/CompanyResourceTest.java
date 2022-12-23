package de.hse.swa.jaxrs.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Address;
import de.hse.swa.orm.model.Company;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CompanyResourceTest {

  private Long current_id;

  private Company createCompanyObject(String prefix){
    Address address = new Address();
    address.setCountry("Germany");
    address.setArea("Baden-Wuettemberg");
    address.setCity("Esslingen");
    address.setZipCode(12345);
    address.setStreetName(prefix + "Falndernstrasse");
    address.setHouseNumber(101);
    Company company = new Company(prefix + "Company", prefix + "Department", null, null, address);

    return company;
  }

  private void createCompanyDatabaseEntry(String prefix){
    Company company = createCompanyObject(prefix);

    this.current_id = given()
                        .contentType("application/json")
                        .body(company)
                      .when()
                        .post("/company/create")
                      .then()
                        .extract()
                          .jsonPath()
                            .getLong("id");
  }

  @BeforeEach
  public void cleanupDatabase(){
    given()
      .when()
      .delete("/company/remove/all");

    createCompanyDatabaseEntry("A_");
  }

  @Test
  public void createCompanyTest(){
    Company company = createCompanyObject("B_");

    given()
      .contentType("application/json")
      .body(company)
    .when()
      .post("/company/create")
    .then()
      .statusCode(200)
      .body("companyName", equalTo("B_Company"));
    
  }

  @Test
  public void getSingleCompanyTest(){
    given()
      .contentType("application/json")
      .pathParam("id", this.current_id)
    .when()
      .get("/company/{id}")
    .then()
      .statusCode(200)
      .body("companyName", equalTo("A_Company"));  
  }

  @Test
  public void getAllCompaniesTest(){
    createCompanyDatabaseEntry("B_");
    createCompanyDatabaseEntry("C_");

    given()
      .contentType("application/json")  
    .when()
      .get("/company/all")
    .then()
      .statusCode(200)
      .body("$.size", equalTo(3));
  }

  @Test
  public void getCompanyByNameTest(){
    given()
      .contentType("application/json")
      .pathParam("companyName", "A_Company")
    .when()
      .get("/company/name/{companyName}")
    .then()
      .statusCode(200)
      .body("id", equalTo(this.current_id.intValue()));
  }

  @Test
  public void getAddressOfCompanyTest(){
    given()
      .pathParam("id", this.current_id)
    .when()
      .get("/company/{id}/address")
    .then()
      .statusCode(200)
      .body(
        "streetName", equalTo("A_Falndernstrasse")
      );
  }

  @Test
  public void updateCompanyTest(){
    Company company = createCompanyObject("A_");
    company.setId(this.current_id);
    company.setCompanyName("Updated Name");
    company.setDepartment("Updated Department");

    given()
      .contentType("application/json")
      .body(company)
    .when()
      .put("/company/update")
    .then()
      .statusCode(200)
      .body(
        "companyName", equalTo("Updated Name"),
        "department", equalTo("Updated Department")
      );
  }

  @Test
  public void deleteCompanyTest(){
    given()
      .contentType("application/json")
    .when()
      .delete("/company/remove/all")
    .then()
      .statusCode(204);
  }

}
