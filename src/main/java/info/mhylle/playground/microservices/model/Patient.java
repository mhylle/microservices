package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.UUID;

@XmlRootElement
public class Patient {
  private UUID identifier;
  private String firstname;
  private String middlename;
  private String familyname;
  private LocalDateTime birthdate;
  private Address address;

  public Patient() {
    identifier = UUID.randomUUID();
  }

  public Patient(UUID identifier) {
    this.identifier = identifier;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public void setMiddlename(String middlename) {
    this.middlename = middlename;
  }

  public String getFamilyname() {
    return familyname;
  }

  public void setFamilyname(String familyname) {
    this.familyname = familyname;
  }

  public LocalDateTime getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDateTime birthdate) {
    this.birthdate = birthdate;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
