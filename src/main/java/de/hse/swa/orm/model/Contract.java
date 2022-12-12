package de.hse.swa.orm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_contract")
public class Contract implements Serializable {
  @Id
  @SequenceGenerator(name="contractSeq", sequenceName="ZSEQ_CONTRACT_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="contractSeq")
  @Column(name="ID", unique=true)
  private Long id;

  @Column(name="LICENSE_KEY", length=512, unique = true)
  private String licenseKey;

  @Column(name="START_DATE")
  @JsonbDateFormat(value="yyyy-MM-dd")
  private LocalDate startDate;

  @Column(name="END_DATE")
  @JsonbDateFormat(value="yyyy-MM-dd")
  private LocalDate endDate;

  @Column(name="VERSION", length=16)
  private String version;

  @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Ip> ips;

  @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Feature> features;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name="CONTRACT_USER",
             joinColumns = @JoinColumn(name="CONTRACT_ID"),
             inverseJoinColumns = @JoinColumn(name="USER_ID"))
  private List<User> users;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="COMPANY_ID", referencedColumnName = "id")
  private Company companyId;

  public Contract(){}

  public Contract(
    String licenseKey, LocalDate startDate, 
    LocalDate endDate, String version, 
    List<Ip> ips, List<Feature> features, 
    List<User> users, Company companyId
  ) {
    this.licenseKey = licenseKey;
    this.startDate = startDate;
    this.endDate = endDate;
    this.version = version;
    this.ips = ips;
    this.features = features;
    this.users = users;
    this.companyId = companyId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLicenseKey() {
    return licenseKey;
  }

  public void setLicenseKey(String licenseKey) {
    this.licenseKey = licenseKey;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public List<Ip> getIps() {
    return ips;
  }

  public void setIps(List<Ip> ips) {
    this.ips = ips;
  }

  public List<Feature> getFeatures() {
    return features;
  }

  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  @JsonbTransient
  public List<User> getUsers(){
    return users;
  }

  public List<Long> getUserIds() {
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

  @JsonbTransient
  public Company getCompany(){
    return companyId;
  }

  public Long getCompanyId() {
    return companyId.getId();
  }

  public void setCompanyId(Company companyId) {
    this.companyId = companyId;
  }

  @Override
  public String toString() {
    return "Contract [\n\tid=" + id + ", \n\tlicenseKey=" + licenseKey + ", \n\tstartDate=" + startDate + ", \n\tendDate=" + endDate
        + ", \n\tversion=" + version + ", \n\tips=" + ips + ", \n\tfeatures=" + features + ", \n\tusers=" + users + ", \n\tcompanyId="
        + companyId + "\n]";
  }
}
