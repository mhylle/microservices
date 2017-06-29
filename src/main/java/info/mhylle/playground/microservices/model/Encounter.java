package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class Encounter {
  private UUID id;
  private String identifier;
  private String responsibleUnit;
  private String status;
  private int priority;

  public Encounter() {
    id = UUID.randomUUID();
  }

  public Encounter(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getResponsibleUnit() {
    return responsibleUnit;
  }

  public void setResponsibleUnit(String responsibleUnit) {
    this.responsibleUnit = responsibleUnit;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }
}
