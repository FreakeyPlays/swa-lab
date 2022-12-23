package de.hse.swa.orm.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hse.swa.orm.model.Address;
import de.hse.swa.orm.model.Company;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CompanyDaoTest {
  @Inject
  CompanyDao _companyDao;

  @Inject
  ContractDao _contractDao;

  @Inject
  UserDao _userDao;

  private Long current_id;

  private Company createCompany(final String name, final String department){
    Company company = new Company();
    Address address = new Address();

    company.setCompanyName(name);
    company.setDepartment(department);
    company.setAddress(address);

    Company res = _companyDao.save(company);
    this.current_id = res.getId();

    return res;
  }

  @BeforeEach
  public void clearCompanyTable(){
    _userDao.removeAllUsers();
    _contractDao.removeAllContracts();
    _companyDao.removeAllCompanies();
  }

  @Test
  public void createCompanyTest(){
    final String companyName = "Test Company GmbH";
    final String departmentName = "IT";
    final int expectedCompanyListSize = 1;
    final int expectedContractListSize = 0;
    final int expectedUsersListSize = 0;

    createCompany(companyName, departmentName);

    assertEquals(expectedCompanyListSize, _companyDao.getAllCompanies().size());

    assertEquals(companyName, _companyDao.getCompanyByName(companyName).getCompanyName());
    assertEquals(departmentName, _companyDao.getCompanyByName(companyName).getDepartment());
    assertEquals(expectedContractListSize, _companyDao.getCompanyByName(companyName).getUsers().size());
    assertEquals(expectedUsersListSize, _companyDao.getCompanyByName(companyName).getContracts().size());
  }

  @Test
  public void createTwoCompaniesTest(){
    final String companyName_A = "Test Company A GmbH";
    final String departmentName_A = "IT";
    final String companyName_B = "Test Company B GmbH";
    final String departmentName_B = "Design";
    final int expectedCompanyListSize = 2;

    createCompany(companyName_A, departmentName_A);
    createCompany(companyName_B, departmentName_B);

    assertEquals(expectedCompanyListSize, _companyDao.getAllCompanies().size());
  }

  @Test
  public void getCompanyByIdTest(){
    final String companyName = "Test Company GmbH";
    final String departmentName = "IT";

    createCompany(companyName, departmentName);
    Company res = _companyDao.getCompanyById(this.current_id);

    assertEquals(companyName, res.getCompanyName());
    assertEquals(departmentName, res.getDepartment());
  }

  @Test
  public void updateCompanyTest(){
    final String companyName = "Test Company GmbH";
    final String departmentName = "IT";

    createCompany("Old Company Name", "Chaos");
    Company company = _companyDao.getCompanyById(this.current_id);
    company.setCompanyName(companyName);
    company.setDepartment(departmentName);
    _companyDao.save(company);

    assertEquals(companyName, _companyDao.getCompanyById(this.current_id).getCompanyName());
    assertEquals(departmentName, _companyDao.getCompanyById(this.current_id).getDepartment());
  }

  @Test
  public void deleteCompanyTest(){
    final String companyName = "Test Company GmbH";
    final String departmentName = "IT";
    Company company = createCompany(companyName, departmentName);

    assertEquals(1, _companyDao.getAllCompanies().size());

    _companyDao.removeCompany(company.getId());

    assertEquals(0, _companyDao.getAllCompanies().size());
  }
}
