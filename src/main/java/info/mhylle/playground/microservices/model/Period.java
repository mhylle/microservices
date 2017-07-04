package info.mhylle.playground.microservices.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.*;

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
  
  @JsonProperty("start")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXXX")
  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }
  
  @JsonProperty("end")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXXX")
  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }
}
