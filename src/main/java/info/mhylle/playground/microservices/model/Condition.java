package info.mhylle.playground.microservices.model;

import java.util.UUID;

public class Condition
{
  private UUID id;
  private String identifier;
  
  private String subject;
  private String severity;
  private String code;
  
  public Condition()
  {
    id = UUID.randomUUID();
  }
  
  public UUID getId()
  {
    return id;
  }
  
  public void setId(UUID id)
  {
    this.id = id;
  }
  
  public String getIdentifier()
  {
    return identifier;
  }
  
  public void setIdentifier(String identifier)
  {
    this.identifier = identifier;
  }
  
  public String getSubject()
  {
    return subject;
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public String getSeverity()
  {
    return severity;
  }
  
  public void setSeverity(String severity)
  {
    this.severity = severity;
  }
  
  public String getCode()
  {
    return code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
}
