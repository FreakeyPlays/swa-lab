package de.hse.swa.orm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_user")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id
  @SequenceGenerator(name="userSeq", sequenceName="ZSEQ_USER_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="userSeq")
  @Column(name="ID", nullable=false, unique=true)
  private Long id;

  @Column(name="LAST_NAME", length=32)
  private String lastName;

  @Column(name="FIRST_NAME", length=32)
  private String firstName;

  @Column(name="USERNAME", length=32, unique = true)
  private String username;

  @Column(name="EMAIL", length=64, unique=true)
  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch =  FetchType.EAGER)
  private List<PhoneNumber> phoneNumbers = new ArrayList<>();

  @Column(name="IS_ADMIN")
  private boolean isAdmin;

  @Column(name="PASSWORD")
  private String password;

  @ManyToMany(mappedBy = "users")
  @JsonbTransient
  private List<Contract> contracts = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="COMPANY_ID", referencedColumnName = "ID")
  private Company companyId;

  public User(){}

  public User(
    String lastName, String firstName, 
    String username, String email,
    List<PhoneNumber> phoneNumbers,
    boolean isAdmin, String password, 
    Company companyId) 
  {
    this.lastName = lastName;
    this.firstName = firstName;
    this.username = username;
    this.email = email;
    this.phoneNumbers = phoneNumbers;
    this.isAdmin = isAdmin;
    this.password = password;
    this.companyId = companyId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id){
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Contract> getContracts() {
    return contracts;
  }

  @JsonbTransient
  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }

  @JsonbTransient
  public Company getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Company companyId) {
    this.companyId = companyId;
  }

  @PreRemove
  public void removeAllContracts(){
    for(Contract contract : this.contracts){
      contract.getUsers().remove(this);
    }
    this.contracts.clear();
  }

  @Override     
  public String toString() {
    return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", username=" + username
        + ", email=" + email + ", phoneNumbers=" + phoneNumbers + ", isAdmin=" + isAdmin + ", password=" + password
        + ", contracts=" + contracts + ", companyId=" + companyId + "]";
  }
}
