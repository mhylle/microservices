package info.mhylle.playground.microservices.model;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address {
  private UUID id;
  private String identifier;
  private String line;
  private String postalCode;
  private String city;
  private String state;
  private int countrycode;

  public Address() {
    id = UUID.randomUUID();
  }
  
  public Address(UUID id)
  {
    this.id = id;
  }
  
  public void setIdentifier(String identifier)
  {
    this.identifier = identifier;
  }
  
  public String getIdentifier() {
    return identifier;
  }
  
  public UUID getId()
  {
    return id;
  }
  
  public void setId(UUID id)
  {
    this.id = id;
  }
  
  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getCountrycode() {
    return countrycode;
  }

  public void setCountrycode(int countrycode) {
    this.countrycode = countrycode;
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
    Address address = (Address) o;
    return Objects.equals(identifier, address.identifier);
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hash(identifier);
  }
}
