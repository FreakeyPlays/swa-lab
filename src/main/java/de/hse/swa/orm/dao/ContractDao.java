package de.hse.swa.orm.dao;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.*;

@ApplicationScoped
public class ContractDao {
  @Inject
  EntityManager entityManager;

  private static final Logger LOGGER = Logger.getLogger(ContractDao.class);

  public List<Contract> getAllContracts() { 
    LOGGER.debug("ContractDao.java(26): getting all Contracts in the Database");

  	TypedQuery<Contract> query = entityManager.createQuery("SELECT contract FROM Contract contract", Contract.class);
  	return query.getResultList();
  }

  public List<Contract> getActiveContracts() {
    LOGGER.debug("ContractDao.java(33): getting all active Contracts in the Database");

  	LocalDate today = LocalDate.now();
    TypedQuery<Contract> query = entityManager.createQuery("SELECT contract FROM Contract contract WHERE contract.endDate >:today", Contract.class);
    query.setParameter("today", today);
    return query.getResultList();
  }

  public List<Contract> getContractsByCompany(Company company) { 
    LOGGER.debug("ContractDao.java(42): getting all Contracts of the Company with id=" + company.getId());

  	TypedQuery<Contract> query = entityManager.createQuery("SELECT contract FROM Contract contract WHERE contract.company.id=:id", Contract.class);
  	query.setParameter("id", company.getId());
  	return query.getResultList();
  }

  public Contract getContract(Long id) { 
    LOGGER.debug("ContractDao.java(50): getting a Contract with id=" + id);

  	 return entityManager.find(Contract.class, id);
  }

  @Transactional
  public Contract save(Contract contract){
    LOGGER.debug("ContractDao.java(57): Creating or Updating a Contract");

    if(contract.getCompanyId() != null){
      contract.setCompanyId(entityManager.find(Company.class, contract.getCompanyId().getId()));
    }
    
    if(contract.getUsers() != null){
      for(int i = 0; i < contract.getUsers().size(); i++){
        contract.getUsers().set(i, entityManager.find(User.class, contract.getUsers().get(i).getId()));
      }
    }
    
    if(contract.getIps() != null){
      for(int i = 0; i < contract.getIps().size(); i++){
        contract.getIps().get(i).setContract(contract);
      }
    }

    if(contract.getFeatures() != null){
      for(int i = 0; i < contract.getFeatures().size(); i++){
        contract.getFeatures().get(i).setContract(contract);
      }
    }

    if(contract.getId() != null){
  	  entityManager.merge(contract);
    } else {
     entityManager.persist(contract);
    }

    return contract;
  }

  @Transactional
  public void removeAllContracts(){
    LOGGER.debug("ContractDao.java(92): removing all Contracts in the Database");

    try{
      entityManager.createQuery("DELETE FROM Feature WHERE id >= 0")
        .executeUpdate();
      entityManager.createQuery("DELETE FROM Ip WHERE id >= 0")
        .executeUpdate();
      entityManager.createQuery("DELETE FROM Contract WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }

  @Transactional
  public void removeContract(Long id) {
    LOGGER.debug("ContractDao.java(110): removing a Contract with id=" + id);

    entityManager.remove(entityManager.find(Contract.class, id));
  }
}
