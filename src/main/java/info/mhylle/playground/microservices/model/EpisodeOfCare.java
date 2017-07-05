package info.mhylle.playground.microservices.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EpisodeOfCare {
  private UUID id;
  private String identifier;
  private String status;
  private String diagnosis;
  private String responsibleUnit;
  private String period;
  private String patient;

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
  
  public String getPeriod()
  {
    return period;
  }
  
  public void setPeriod(String period)
  {
    this.period = period;
  }
  
  public String getPatient()
  {
    return patient;
  }
  
  public void setPatient(String patient)
  {
    this.patient = patient;
  }
  
  public String getDiagnosis()
  {
    return diagnosis;
  }
  
  public void setDiagnosis(String diagnosis)
  {
    this.diagnosis = diagnosis;
  }
}
