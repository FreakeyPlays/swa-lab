package de.hse.swa.orm.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hse.swa.orm.model.Company;

@ApplicationScoped
public class CompanyDao {
  @Inject
  EntityManager entityManager; 

  private static final Logger LOGGER = Logger.getLogger(CompanyDao.class);

  public List<Company> getAllCompanies() {
    LOGGER.debug("CompanyDao.java(23): getting all Companies in the Database");

  	TypedQuery<Company> query = entityManager.createQuery("SELECT company FROM Company company", Company.class);
  	return query.getResultList();
  }

  public Company getCompanyById(Long id) {
    LOGGER.debug("CompanyDao.java(30): getting a Company with id=" + id);

	 	return entityManager.find(Company.class, id);
  }

  public Company getCompanyByName(String name) {
    LOGGER.debug("CompanyDao.java(36): getting a Company with name=" + name);

	 	TypedQuery<Company> query = entityManager.createQuery("SELECT company FROM Company company where company.companyName=:name", Company.class);
    query.setParameter("name", name);
    return query.getSingleResult();
  }

  @Transactional
  public Company save(Company company){
    LOGGER.debug("CompanyDao.java(45): Creating or Updating a Company");

    company.getAddress().setCompany(company);

    if(company.getId() != null){
  	  entityManager.merge(company);
    } else {
      entityManager.persist(company);
    }

    return company;
  }

  @Transactional
  public void removeAllCompanies(){
    LOGGER.debug("CompanyDao.java(60): removing all Companies from the Database");

    try{
      entityManager.createQuery("DELETE FROM Address WHERE id >= 0")
        .executeUpdate();
      entityManager.createQuery("DELETE FROM Company WHERE id >= 0")
        .executeUpdate();
    } catch (SecurityException | IllegalStateException e){
      e.printStackTrace();
    }

    return;
  }

  @Transactional 
  public void removeCompany(Long id) {
    LOGGER.debug("CompanyDao.java(76): removing a Company with id=" + id);

    entityManager.remove(entityManager.find(Company.class, id));
  }
}
