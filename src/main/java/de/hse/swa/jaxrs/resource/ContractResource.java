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

import de.hse.swa.orm.dao.ContractDao;
import de.hse.swa.orm.dao.FeatureDao;
import de.hse.swa.orm.dao.IpDao;
import de.hse.swa.orm.model.Company;
import de.hse.swa.orm.model.Contract;
import de.hse.swa.orm.model.Feature;
import de.hse.swa.orm.model.Ip;
import de.hse.swa.orm.model.User;

@Path("/contract")
public class ContractResource {
  @ApplicationScoped

  @Inject
  ContractDao _contractDao;

  @Inject
  IpDao _ipDao;

  @Inject
  FeatureDao _featureDao;

  /**
   * Create a new Contract by Data from Body
   * @param contract
   * @return the new Contract
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("create")
  public Contract createContract(Contract contract){
    Contract newContract = _contractDao.save(contract);

    for(int i = 0; i < contract.getIps().size(); i++){
      Ip newIp = contract.getIps().get(i);

      newIp.setContract(newContract);
      _ipDao.addIp(newIp);
    }

    for(int i = 0; i < contract.getFeatures().size(); i++){
      Feature newFeature = contract.getFeatures().get(i);

      newFeature.setContract(newContract);
      _featureDao.addFeature(newFeature);
    }

    return contract;
  }

  /**
   * Get all Contracts
   * @return a List of all Contracts
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("all")
  public List<Contract> getAllContracts(){
    return _contractDao.getAllContracts();
  }

  /**
   * Get all Contracts
   * @return a List of all Contracts
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("all/active")
  public List<Contract> getAllActiveContracts(){
    return _contractDao.getActiveContracts();
  }

  /**
   * Get a Contract by ID
   * @param id
   * @return a Contract
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public Contract getContract(@PathParam("id") Long id){
    return _contractDao.getContract(id);
  }

  /**
   * Get the Company of the Contract
   * @param id
   * @return a Company
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/company")
  public Company getCompanyByContract(@PathParam("id") Long id){
    return _contractDao.getContract(id).getCompany();
  }

  /**
   * Get the User of the Contract
   * @param id
   * @return a List of User
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/user")
  public List<User> getUserByContract(@PathParam("id") Long id){
    return _contractDao.getContract(id).getUsers();
  }

  /**
   * Get the Ips of the Contract
   * @param id
   * @return a List of Ips
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/ips")
  public List<Ip> getIpsByContract(@PathParam("id") Long id){
    return _contractDao.getContract(id).getIps();
  }

  /**
   * Get the Features of the Contract
   * @param id
   * @return a List of Features
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}/features")
  public List<Feature> getFeaturesByContract(@PathParam("id") Long id){
    return _contractDao.getContract(id).getFeatures();
  }

  /**
   * Update a Contract by Data from the Body
   * @param contract
   * @return the updated Contract
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("update")
  public Contract updateContract(Contract contract){
    return _contractDao.save(contract);
  }

  /**
   * Delete all Contracts
   */
  @DELETE
  @Path("remove/all")
  public void removeAllCompanies(){
    _contractDao.removeAllContracts();
  }

  /**
   * Delete Contract by ID from Path
   * @param id
   */
  @DELETE
  @Path("remove/{id}")
  public void removeContract(@PathParam("id") Long id){
    _contractDao.removeContract(id);
  }
}
