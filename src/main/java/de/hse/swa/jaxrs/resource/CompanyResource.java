package de.hse.swa.jaxrs.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.hse.swa.orm.dao.CompanyDao;
import de.hse.swa.orm.dao.ContractDao;
import de.hse.swa.orm.dao.UserDao;
import de.hse.swa.orm.model.Address;
import de.hse.swa.orm.model.Company;
import de.hse.swa.orm.model.Contract;
import de.hse.swa.orm.model.User;

@Path("/company")
public class CompanyResource {
  @ApplicationScoped

  @Inject
  CompanyDao _companyDao;

  @Inject
  UserDao _userDao;

  @Inject
  ContractDao _contractDao;

  /**
   * Creates a new Company with Data from the Body
   * @param company
   * @return the new Company
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("create")
  public Company createCompany(Company company){
    _companyDao.save(company);
    return company;
  }

  /**
   * Get all Companies in the Database
   * @return List of Companies
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("all")
  public List<Company> getAllCompanies(){
    return _companyDao.getAllCompanies();
  }

  /**
   * Get a Company by Company ID
   * @param id
   * @return a Company
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public Company getCompanyById(@PathParam("id") Long id){
    return _companyDao.getCompanyById(id);
  }

  /**
   * Get a Company by Company Company Name
   * @param id
   * @return a Company
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("name/{companyName}")
  public Company getCompanyByName(@PathParam("companyName") String companyName){
    return _companyDao.getCompanyByName(companyName);
  }

  /**
   * Get a Company by Company ID
   * @param id
   * @return a List of Companies
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/users")
  public List<User> getUsersOfCompany(@PathParam("id") Long id){
    return _companyDao.getCompanyById(id).getUsers();
  }

  /**
   * Get a Company by Company ID
   * @param id
   * @return a List of Companies
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/contracts")
  public List<Contract> getContractsOfCompany(@PathParam("id") Long id){
    return _companyDao.getCompanyById(id).getContracts();
  }

  /**
   * Get the Address of the Company
   * @param id
   * @return a Address
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/address")
  public Address getAddressOfCompany(@PathParam("id") Long id){
    return _companyDao.getCompanyById(id).getAddress();
  }

  /**
   * Updates a Company with Data from Body
   * @param company
   * @return the updated Company
   */
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("update")
  public Company updateCompany(Company company){
    return _companyDao.save(company);
  }

  /**
   * Delete all Companies
   */
  @DELETE
  @Path("remove/all")
  public void removeAllCompanies(){
    _userDao.removeAllUsers();
    _contractDao.removeAllContracts();
    _companyDao.removeAllCompanies();
  }

  /**
   * Deletes a Company by ID
   * @param id
   */
  @DELETE
  @Path("remove/{id}")
  public void removeCompany(@PathParam("id") Long id){
    _companyDao.removeCompany(id);
  }
}
