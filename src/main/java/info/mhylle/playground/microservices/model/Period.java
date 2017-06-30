package info.mhylle.playground.microservices.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import info.mhylle.playground.microservices.adaptors.LocalDateTimeAdapter;

@XmlRootElement
public class Period {
  private UUID identifier;
  private LocalDateTime start;
  private LocalDateTime end;

  public Period() {
    identifier = UUID.randomUUID();
  }

  public UUID getIdentifier() {
    return identifier;
  }
  
  @XmlElement(name = "start")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }
  
  @XmlElement(name = "end")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }
}
