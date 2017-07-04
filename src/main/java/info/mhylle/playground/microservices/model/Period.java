package info.mhylle.playground.microservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.mhylle.playground.microservices.adaptors.LocalDateTimeDeSerializer;
import info.mhylle.playground.microservices.adaptors.LocalDateTimeSerializer;

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

  @JsonProperty("start")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeSerializer.class)
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  @JsonProperty("end")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeSerializer.class)
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }
}
