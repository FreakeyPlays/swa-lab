package de.hse.swa.orm.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_ips")
public class Ip implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name="ipSeq", sequenceName="ZSEQ_IP_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="ipSeq")
  @Column(name="ID", nullable=false, unique=true)
  private Long id;

  @Column(name="ADDRESS")
  private String address;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="CONTRACT_ID", referencedColumnName = "id")
  @JsonbTransient
  private Contract contract;

  public Ip(){}

  public Ip(String address) {
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  @Override
  public String toString() {
    return "Ip [id=" + id + ", address=" + address + "]";
  }
}
