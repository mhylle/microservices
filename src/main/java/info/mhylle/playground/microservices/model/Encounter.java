package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class Encounter {
  private UUID identifier;
  private int responsibleUnit;
  private int status;
  private int priority;

  public Encounter() {
    identifier = UUID.randomUUID();
  }

  public Encounter(UUID identifier) {
    this.identifier = identifier;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public int getResponsibleUnit() {
    return responsibleUnit;
  }

  public void setResponsibleUnit(int responsibleUnit) {
    this.responsibleUnit = responsibleUnit;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }
}
