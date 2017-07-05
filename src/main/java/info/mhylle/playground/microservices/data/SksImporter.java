package info.mhylle.playground.microservices.data;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * Created by mnh on 05-07-2017.
 */
public class SksImporter
{
  
  private String jsonFile;
  private String fileName;
  private String json;
  private int counter;
  
  public static void main(String[] args)
  {
    SksImporter importer = new SksImporter();
    importer.doImport();
  }
  
  private void doImport()
  {
    json = "[";
    counter = 1;
    fileName = "resources/skssickdump.txt";
    jsonFile="resources/sksSickCodes.json";
    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
      
      stream.forEach((String s) -> createJsonElement(s));
    } catch (IOException e) {
      e.printStackTrace();
    }
    json = json.substring(0, json.length() - 1);
    json += "]";
    System.out.println("json = " + json);
    try {
      Files.write(Paths.get(jsonFile), json.getBytes(), StandardOpenOption.CREATE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
  private void createJsonElement(String s) {
    String[] split = s.split("\t");
    try {
      if ("".equals(split[0])) {
        return;
      }
      json += "{\"id\": \"" + counter++ + "\", \"code\": \"" + split[0] + "\",\"text\": \"" + split[1] + "\"},";
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
