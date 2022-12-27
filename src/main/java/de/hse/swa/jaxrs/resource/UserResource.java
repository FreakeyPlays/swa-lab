package de.hse.swa.jaxrs.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.hse.swa.orm.dao.*;
import de.hse.swa.orm.model.*;

@Path("/user")
public class UserResource {
  @ApplicationScoped

  @Inject
  UserDao _userDao;

  @Inject
  CompanyDao _companyDao;

  /**
   * Creates a new User based on Data from the Body
   * @param user
   * @return the new User
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("create")
  public User createUser(User user){
    return _userDao.save(user);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("login")
  public Boolean loginUser(
    @QueryParam("username") String username, 
    @QueryParam("password") String password
  ){
    return _userDao.loginUser(username, password);
  }

  /**
   * Get all Users in the Database
   * @return a List of all Users
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("all")
  public List<User> getAllUsers(){
    return _userDao.getAllUsers();
  }

  /**
   * Returns all User of the Company
   * @param companyId
   * @return a List of Users in Company
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("all/company/{id}")
  public List<User> getUserByCompany(@PathParam("id") Long companyId){
    return _userDao.getUsersByCompany(companyId);
  }

  /**
   * Get User by ID
   * @param id
   * @return a User
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public User getUser(@PathParam("id") Long id){
    return _userDao.getUserById(id);
  }

  /**
   * Get User by Username
   * @param username
   * @return a User
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("username/{username}")
  public User getUser(@PathParam("username") String username){
    return _userDao.getUserByUsername(username);
  }

  /**
   * Get the List of Phone Numbers of the User
   * @param id
   * @return a List of Phone Numbers
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/phone")
  public List<PhoneNumber> getPhoneNumberOfUser(@PathParam("id") Long id){
    return _userDao.getUserById(id).getPhoneNumbers();
  }

  /**
   * Get the List of Contracts of the User
   * @param id
   * @return a List of Contracts
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/contracts")
  public List<Contract> getContractsOfUser(@PathParam("id") Long id){
    return _userDao.getUserById(id).getContracts();
  }

  /**
   * Get the Company of the User
   * @param id
   * @return a Company
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/company")
  public Company getCompanyOfUser(@PathParam("id") Long id){
    return _userDao.getUserById(id).getCompanyId();
  }

  /**
   * Updates a User with Data from the Body
   * @return the Updated User
   */
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("update")
  public User updateUser(User user){
    return _userDao.save(user);
  }

  /**
   * Delete all Users
   */
  @DELETE
  @Path("remove/all")
  public void removeAllCompanies(){
    _userDao.removeAllUsers();
  }

  /**
   * Deletes user by ID
   * @param id
   */
  @DELETE
  @Path("remove/{id}")
  public void removeUser(@PathParam("id") Long id){
    _userDao.removeUser(id);
  }
}
