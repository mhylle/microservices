package info.mhylle.playground.microservices.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Encounter {
  private UUID id;
  private String identifier;
  private String responsibleUnit;
  private String status;
  private int priority;
  private String patient;
  private String episodeOfCare;
  private String diagnosis;

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
  
  public String getPatient()
  {
    return patient;
  }
  
  public void setPatient(String patient)
  {
    this.patient = patient;
  }
  
  public String getEpisodeOfCare()
  {
    return episodeOfCare;
  }
  
  public void setEpisodeOfCare(String episodeOfCare)
  {
    this.episodeOfCare = episodeOfCare;
  }

  public String getDiagnosis() {
    return diagnosis;
  }

  public void setDiagnosis(String diagnosis) {
    this.diagnosis = diagnosis;
  }

}
