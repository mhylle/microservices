package info.mhylle.playground.microservices.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Encounter
{
  UUID identifier;
  int responsibleUnit;
  int status;
  int priority;
}
