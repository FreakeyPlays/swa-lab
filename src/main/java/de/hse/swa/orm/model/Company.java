package de.hse.swa.orm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_company")
public class Company implements Serializable {
  @Id
  @SequenceGenerator(name="companySeq", sequenceName="ZSEQ_COMPANY_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="companySeq")
  @Column(name="ID", unique=true)
  private Long id;

  @Column(name="COMPANY_NAME", unique=true, length=64)
  public String companyName;

  @Column(name="DEPARTMENT", length=64)
  private String department;

  @OneToMany(mappedBy = "companyId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Contract> contracts;

  @OneToMany(mappedBy = "companyId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<User> users;

  @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  private Address address;

  public Company(){}

  public Company(
    String companyName, String department, 
    List<Contract> contracts, List<User> users, 
    Address address) 
  {
    this.companyName = companyName;
    this.department = department;
    this.contracts = contracts;
    this.users = users;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  @JsonbTransient
  public List<Contract> getContractObjects(){
    return contracts;
  }

  public List<Long> getContracts() {
    List<Long> list = new ArrayList<>();

    if(contracts == null){
      return list;
    }

    for(int i = 0; i < contracts.size(); i++){
      list.add(contracts.get(i).getId());
    }

    return list;
  }

  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }

  @JsonbTransient
  public List<User> getUserObjects(){
    return users;
  }

  public List<Long> getUsers() {
    List<Long> list = new ArrayList<>();

    if(users == null){
      return list;
    }

    for(int i = 0; i < users.size(); i++){
      list.add(users.get(i).getId());
    }
    
    return list;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  } 

  @Override
  public String toString() {
    return "Company [id=" + id + ", companyName=" + companyName + ", department=" + department + ", contracts="
        + contracts + ", users=" + users + ", address=" + address + "]";
  }
}
