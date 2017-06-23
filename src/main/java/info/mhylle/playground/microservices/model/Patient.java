package info.mhylle.playground.microservices.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Patient
{
  UUID identifier;
  String firstname;
  String middlename;
  String familyname;
  LocalDateTime birthdate;
}
