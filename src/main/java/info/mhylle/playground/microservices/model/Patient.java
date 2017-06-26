package info.mhylle.playground.microservices.model;

import java.time.LocalDateTime;
import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Patient
{
  private UUID id;
  private String identifier;
  private String firstname;
  private String middlename;
  private String familyname;
  private LocalDateTime birthdate;
  private Address address;
  
  public Patient()
  {
    id = UUID.randomUUID();
  }
  
  public Patient(UUID id)
  {
    this.id = id;
  }
  
  public UUID getId()
  {
    return id;
  }
  
  public void setId(UUID id)
  {
    this.id = id;
  }
  
  public String getIdentifier()
  {
    return identifier;
  }
  
  public void setIdentifier(String identifier)
  {
    this.identifier = identifier;
  }
  
  public String getFirstname()
  {
    return firstname;
  }
  
  public void setFirstname(String firstname)
  {
    this.firstname = firstname;
  }
  
  public String getMiddlename()
  {
    return middlename;
  }
  
  public void setMiddlename(String middlename)
  {
    this.middlename = middlename;
  }
  
  public String getFamilyname()
  {
    return familyname;
  }
  
  public void setFamilyname(String familyname)
  {
    this.familyname = familyname;
  }
  
  public LocalDateTime getBirthdate()
  {
    return birthdate;
  }
  
  public void setBirthdate(LocalDateTime birthdate)
  {
    this.birthdate = birthdate;
  }
  
  public Address getAddress()
  {
    return address;
  }
  
  public void setAddress(Address address)
  {
    this.address = address;
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Patient patient = (Patient) o;
    return Objects.equals(identifier, patient.identifier);
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hash(identifier);
  }
}
