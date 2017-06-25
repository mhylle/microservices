package info.mhylle.playground.microservices.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.UUID;

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

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }
}
