package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class EpisodeOfCare {
  private UUID id;
  private String identifier;
  private String responsibleUnit;
  private String status;

  public EpisodeOfCare() {
    id = UUID.randomUUID();
  }

  public EpisodeOfCare(UUID id) {
    this.id = id;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
}
