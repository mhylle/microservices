package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class EpisodeOfCare {
  private UUID identifier;
  private SORCode responsibleUnit;
  private String status;

  public EpisodeOfCare() {
    identifier = UUID.randomUUID();
  }

  public EpisodeOfCare(UUID identifier) {
    this.identifier = identifier;
  }

  public UUID getIdentifier() {
    return identifier;
  }

  public SORCode getResponsibleUnit() {
    return responsibleUnit;
  }

  public void setResponsibleUnit(SORCode responsibleUnit) {
    this.responsibleUnit = responsibleUnit;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
