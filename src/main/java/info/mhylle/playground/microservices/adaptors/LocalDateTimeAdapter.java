package info.mhylle.playground.microservices.adaptors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>
{
  
  @Override
  public LocalDateTime unmarshal(String dateString) throws Exception
  {
    System.out.println("dateString = " + dateString);
    try {
      DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
      LocalDateTime parse1 = LocalDateTime.parse(dateString, dtf);
      return parse1;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  @Override
  public String marshal(LocalDateTime dateTime) throws Exception
  {
    System.out.println("dateTime = " + dateTime);
    if (dateTime != null) {
      return DateTimeFormatter.ISO_DATE_TIME.format(dateTime);
    }
    return null;
  }
}
