package de.hse.swa.orm.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name="T_phoneNumber")
public class PhoneNumber implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name="phoneNumberSeq", sequenceName="ZSEQ_PHONENUMBER_ID", allocationSize=1, initialValue=10)
  @GeneratedValue(generator="phoneNumberSeq")
  @Column(name="ID", nullable=false, unique=true)
  private Long id;

  @Column(name="NUMBER")
  private String number;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="USER_ID", referencedColumnName = "id")
  @JsonbTransient
  private User user;

  public PhoneNumber(){}

  public PhoneNumber(String number) {
    this.number = number;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id){
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user){
    this.user = user;
  }

  @Override
  public String toString() {
    return "PhoneNumber [id=" + id + ", number=" + number + ", User=" + user + "]";
  }
}
