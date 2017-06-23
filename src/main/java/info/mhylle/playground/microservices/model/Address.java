package info.mhylle.playground.microservices.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address
{
  UUID identifier;
  String line;
  String postalCode;
  String city;
  String state;
  int countrycode;
}
