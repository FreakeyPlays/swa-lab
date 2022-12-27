package de.hse.swa.orm.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name="T_feature")
public class Feature implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name="featureSeq", sequenceName="ZSEQ_FEATURE_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="featureSeq")
  @Column(name="ID", nullable=false, unique=true)
  private Long id;

  @Column(name="NUMBER")
  private int number;

  @ManyToOne
  @JoinColumn(name="CONTRACT_ID", referencedColumnName = "ID")
  @JsonbTransient
  private Contract contract;

  public Feature(){}

  public Feature(int number) {
    this.number = number;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id){
    this.id = id;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  @Override
  public String toString() {
    return "Feature [id=" + id + ", number=" + number + "]";
  }
}
