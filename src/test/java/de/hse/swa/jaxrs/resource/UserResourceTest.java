package de.hse.swa.jaxrs.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Address;
import de.hse.swa.orm.model.Company;
import de.hse.swa.orm.model.User;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserResourceTest {
  private Long current_id;
  private Long company_id;

  private final String default_lastName = "Mustermann";
  private final String default_firstName = "Max";
  private final String default_username = "Muster";
  private final String default_email = "max.muster@muster.de";
  private final boolean default_isAdmin = true;
  private final String default_password = "password";

  private User createUserObject(String prefix){
    User user = new User();
    user.setFirstName(prefix + this.default_firstName);
    user.setLastName(prefix + this.default_lastName);
    user.setUsername(prefix + this.default_username);
    user.setEmail(prefix + this.default_email);
    user.setIsAdmin(this.default_isAdmin);
    user.setPassword(prefix + this.default_password);
    Company company = createCompany(prefix);
    user.setCompanyId(company);

    return user;
  }

  private Company createCompanyObject(String prefix){
    Address address = new Address();
    address.setCountry("Germany");
    address.setArea("Baden-Wuettemberg");
    address.setCity("Esslingen");
    address.setZipCode(12345);
    address.setStreetName("Falndernstrasse");
    address.setHouseNumber(101);
    Company company = new Company(prefix + "Company", "Department", null, null, address);

    return company;
  }

  private Company createCompany(String prefix){
    Company company = createCompanyObject(prefix);

    this.company_id = 
    given()
     .contentType("application/json")
     .body(company)
    .when()
     .post("/company/create")
    .then()
     .statusCode(200)
    .extract()
     .jsonPath()
       .getLong("id");

    return company;
  }

  private User createUserDatabaseEntry(String prefix){
    User user = createUserObject(prefix);
    
    this.current_id = 
      given()
        .contentType("application/json")
        .body(user)
      .when()
        .post("/user/create")
      .then()
        .statusCode(200)
      .extract()
        .jsonPath()
          .getLong("id");

    return user;
  }

  @BeforeEach
  public void cleanupDatabase(){
    given()
    .when()
      .delete("/user/remove/all");

    given()
    .when()
      .delete("/company/remove/all");
  }

  @Test
  public void createUserTest(){
    final String prefix = "A_";
    User user = createUserObject(prefix);

    given()
      .contentType("application/json")
      .body(user)
    .when()
      .post("/user/create")
    .then()
      .statusCode(200)
      .body("$", hasKey("id"));
  }

  @Test
  public void loginUserTest(){
    final String prefix = "A_";
    User user = createUserDatabaseEntry(prefix);

    given()
      .contentType("application/json")
      .queryParam("username", user.getUsername())
      .queryParam("password", user.getPassword())
    .when()
      .post("/user/login")
    .then()
      .statusCode(200)
      .body(equalTo("true"));
  }

  @Test
  public void getAllUsersTest(){
    final String[] prefixes = {"A_", "B_", "C_"};
    for(int i = 0; i < prefixes.length; i++){
      createUserDatabaseEntry(prefixes[i]);
    }

    given()
      .contentType("application/json")
    .when()
      .get("/user/all")
    .then()
      .statusCode(200)
      .body("$.size", equalTo(prefixes.length));
  }

  //TODO: Get all Users of Company

  @Test
  public void getUserByIdTest(){
    final String prefix = "A_";
    User user = createUserDatabaseEntry(prefix);

    given()
      .contentType("application/json")
      .pathParam("id", this.current_id)
    .when()
      .get("/user/{id}")
    .then()
      .statusCode(200)
      .body("id.longValue()", equalTo(this.current_id));
  }

  @Test
  public void getUserByUsernameTest(){
    final String prefix = "A_";
    User user = createUserDatabaseEntry(prefix);

    given()
      .contentType("application/json")
      .pathParam("username", user.getUsername())
    .when()
      .get("/user/username/{username}")
    .then()
      .statusCode(200)
      .body("id.longValue()", equalTo(this.current_id));
  }

  //TODO: Get PhoneNumbers of User

  //TODO: Get Contracts of User

  //TODO: Get Company of User

  @Test
  public void updateUserTest(){
    final String prefix = "A_";
    final String updatedFistName = "Updated Max";
    User user = createUserDatabaseEntry(prefix);
    user.setId(this.current_id);
    user.setFirstName(updatedFistName);

    given()
      .contentType("application/json")
      .body(user)
    .when()
      .put("/user/update")
    .then()
      .statusCode(200)
      .body("firstName", equalTo(updatedFistName));
  }

  //TODO: Remove all Users

  @Test
  public void deleteUserTest(){
    final String prefix = "A_";
    User user = createUserDatabaseEntry(prefix);

    given()
      .contentType("application/json")
      .pathParam("id", this.current_id)
    .when()
      .delete("/user/remove/{id}")
    .then()
      .statusCode(204);
  }
}
