package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class Address {
  private UUID identifier;
  private String line;
  private String postalCode;
  private String city;
  private String state;
  private int countrycode;

  public Address() {
    identifier = UUID.randomUUID();
  }

  public Address(UUID identifier) {
    this.identifier = identifier;
  }

  public UUID getIdentifier() {
    return identifier;
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
}
