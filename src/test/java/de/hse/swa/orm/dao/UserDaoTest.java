package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.User;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserDaoTest {
  @Inject
  UserDao _userDao;

  private Long current_id;

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
    user.setIsAdmin(default_isAdmin);
    user.setPassword(prefix + this.default_password);
    
    return user;
  }  

  private User createUserInDatabase(String prefix){
    User user = createUserObject(prefix);

    user = _userDao.save(user);
    this.current_id = user.getId();

    return user;
  }

  @BeforeEach
  public void clearDatabase(){
    _userDao.removeAllUsers();
  }

  @Test
  public void createUserTest(){
    final String prefix = "A_";

    User user = createUserInDatabase(prefix);

    assertNotNull(user.getId());
  }

  @Test
  public void getUserByIdTest(){
    final String prefix = "A_";

    createUserInDatabase(prefix);

    assertNotNull(_userDao.getUserById(this.current_id));
  }

  @Test
  public void getAllUsersTest(){
    final String[] prefixes = {"A_", "B_", "C_"};

    for(int i = 0; i < prefixes.length; i++){
      createUserInDatabase(prefixes[i]);
    }

    assertEquals(prefixes.length, _userDao.getAllUsers().size());
  }

  @Test
  public void getUserByUsernameTest(){
    final String prefix = "A_";

    createUserInDatabase(prefix);

    assertNotNull(_userDao.getUserByUsername(prefix + this.default_username));
  }

  @Test
  public void updateUserTest(){
    final String prefix = "A_";
    final String updatedUsername = "Updated Muster";
    createUserInDatabase(prefix);
    User user = _userDao.getUserById(this.current_id);

    user.setUsername(updatedUsername);
    _userDao.save(user);

    assertEquals(updatedUsername, _userDao.getUserById(this.current_id).getUsername());
  }

  @Test
  public void removeUserTest(){
    final String prefix = "A_";
    createUserInDatabase(prefix);

    _userDao.removeUser(this.current_id);

    assertNull(_userDao.getUserById(this.current_id));
  }

  @Test
  public void removeAllUsersTest(){
    final String[] prefixes = {"A_", "B_", "C_"};

    for(int i = 0; i < prefixes.length; i++){
      createUserInDatabase(prefixes[i]);
    }

    _userDao.removeAllUsers();
    assertEquals(0, _userDao.getAllUsers().size());
  }

  @Test
  public void loginUserTest(){
    final String prefix = "A_";
    
    createUserInDatabase(prefix);

    assertTrue(_userDao.loginUser(prefix + this.default_username, prefix + this.default_password));
  }
}
