package de.hse.swa.orm.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_address")
public class Address implements Serializable {
  @Id
  @SequenceGenerator(name="addressSeq", sequenceName="ZSEQ_ADDRESS_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="addressSeq")
  @Column(name="ID", unique=true)
  private Long id;

  @Column(name="COUNTRY", length=64)
  private String country;

  @Column(name="AREA", length=64)
  private String area;

  @Column(name="CITY", length=64)
  private String city;

  @Column(name="ZIP_CODE")
  private int zipCode;

  @Column(name="STREET_NAME", length=64)
  private String streetName;

  @Column(name="HOUSE_NUMBER")
  private int houseNumber;

  @OneToOne
  @JoinColumn(name = "COMPANY_ID")
  @JsonbTransient
  private Company company;

  public Address(){}

  public Address(
    String country, String area, 
    String city, int zipCode, 
    String streetName, int houseNumber,
    Company company) 
  {
    this.country = country;
    this.area = area;
    this.city = city;
    this.zipCode = zipCode;
    this.streetName = streetName;
    this.houseNumber = houseNumber;
    this.company = company;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getZipCode() {
    return zipCode;
  }

  public void setZipCode(int zipCode) {
    this.zipCode = zipCode;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public int getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(int houseNumber) {
    this.houseNumber = houseNumber;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    return "Address [id=" + id + ", country=" + country + ", area=" + area + ", city=" + city + ", zipCode=" + zipCode
        + ", streetName=" + streetName + ", houseNumber=" + houseNumber + ", company=" + company + "]";
  }
}
