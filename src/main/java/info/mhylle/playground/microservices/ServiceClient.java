package info.mhylle.playground.microservices;

import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.*;

import info.mhylle.playground.microservices.model.*;
import info.mhylle.playground.microservices.model.Period;

public class ServiceClient
{
  private static final int NR_OF_PATIENTS = 5;
  private static final int NR_OF_ADDRESSES = 5;
  private static final int NR_OF_EPISODESOFCARE = 5;
  private static final int NR_OF_ENCOUNTERS = 5;
  private static final int NR_OF_PERIODS = 5;
  private List<String> firstnames;
  private List<String> lastnames;
  private List<String> cities;
  private List<String> states;
  private List<String> streetnumbers;
  private List<String> streets;
  private List<Code> status;
  private List<Code> sksCodes;
  
  public static void main(String[] args)
  {
    ServiceClient serviceClient = new ServiceClient();
    serviceClient.start();
  }
  
  private void start()
  {
    firstnames = new ArrayList<>();
    lastnames = new ArrayList<>();
    cities = new ArrayList<>();
    states = new ArrayList<>();
    streetnumbers = new ArrayList<>();
    streets = new ArrayList<>();
    status = new ArrayList<>();
    sksCodes = new ArrayList<>();
    int patientCount = getPatientCount();
    int addressCount = getAddressCount();
    System.out.println("patientCount = " + patientCount);
    System.out.println("addressCount = " + addressCount);
    readData(firstnames, "firstnames", "firstname");
    readData(lastnames, "lastnames", "lastname");
    readData(cities, "cities", "city");
    readData(states, "states", "state");
    readData(streetnumbers, "streetnumbers", "streetnumber");
    readData(streets, "streets", "streetname");
    readCode(status, "status", "code");
    readCode(sksCodes, "skscodes", "code");
    createPeriods();
    createAddresses();
    long startTime = System.nanoTime();
    createPatients();
    long endTime = System.nanoTime();
    long elapsed = endTime - startTime;
    System.out.println("elapsed time= " + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0);
    createEpisodesOfCare();
    createEncounters();
  }
  
  private void readData(
    List<String> list,
    String type,
    String attribute)
  {
    
    try {
      InputStream in = new FileInputStream(".\\resources\\" + type + ".json");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder json = new StringBuilder();
      String data;
      while ((data = reader.readLine()) != null) {
        json.append(data);
      }
      JSONArray array = new JSONArray(json.toString());
      for (int i = 0; i < array.length(); i++) {
        JSONObject o = (JSONObject) array.get(i);
        
        String state = (String) o.get(attribute);
        list.add(state);
      }
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }
  }
  
  private void readCode(
    List<Code> list,
    String type,
    String attribute)
  {
    
    try {
      InputStream in = new FileInputStream(".\\resources\\" + type + ".json");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder json = new StringBuilder();
      String data;
      while ((data = reader.readLine()) != null) {
        json.append(data);
      }
      JSONArray array = new JSONArray(json.toString());
      for (int i = 0; i < array.length(); i++) {
        JSONObject o = (JSONObject) array.get(i);
        
        String id = (String) o.get("id");
        String code = (String) o.get(attribute);
        list.add(new Code(id, code));
      }
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createPatients()
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/patients");
      
      long startTime = System.nanoTime();
      for (int i = 0; i < NR_OF_PATIENTS; i++) {
        Patient p = createNewPatient();
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
          String s = mapper.writeValueAsString(p);
          wr.write(s.getBytes());
        }
        connection.getResponseCode();
        connection.getResponseMessage();
        //        System.out.println("responseCode = " + responseCode + " Message: " + responseMessage);
        
      }
      long endTime = System.nanoTime();
      long elapsed = endTime - startTime;
      System.out.println(
        "Created " + NR_OF_PATIENTS + " patients in "
          + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0 + " seconds");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createPeriods()
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/periods");
      
      long startTime = System.nanoTime();
      for (int i = 0; i < NR_OF_PERIODS; i++) {
        Period p = createNewPeriod();
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
          String s = mapper.writeValueAsString(p);
          System.out.println("s = " + s);
          wr.write(s.getBytes());
        }
        int responseCode = connection.getResponseCode();
        String responseMessage = connection.getResponseMessage();
        InputStream errorStream = connection.getErrorStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
          String buffer;
          String error = "";
          while ((buffer = reader.readLine()) != null) {
            error += buffer;
          }
          System.out.println("error = " + error);
        }
        //        System.out.println("responseCode = " + responseCode + " Message: " + responseMessage);
        
      }
      long endTime = System.nanoTime();
      long elapsed = endTime - startTime;
      System.out.println(
        "Created " + NR_OF_PERIODS + " Periods in "
          + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0 + " seconds");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createAddresses()
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/addresses");
      String nextAddressIdentifier = getNextAddressIdentifier();
      int nextAddrId = 0;
      if (nextAddressIdentifier != null && !"".equals(nextAddressIdentifier)) {
        nextAddrId = Integer.parseInt(nextAddressIdentifier);
      }
      
      for (int i = 0; i < NR_OF_ADDRESSES; i++) {
        Address p = createNewAddress(nextAddrId + i);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
          String s = mapper.writeValueAsString(p);
          wr.write(s.getBytes());
        }
        connection.getResponseCode();
        connection.getResponseMessage();
        //        System.out.println("responseCode = " + responseCode + " Message: " + responseMessage);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createEpisodesOfCare()
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/episodesofcare");
      String nextEpisodeOfCareIdentifier = getNextEpisodeOfCareIdentifier();
      int nextEocId = 0;
      if (nextEpisodeOfCareIdentifier != null && !"".equals(nextEpisodeOfCareIdentifier)) {
        nextEocId = Integer.parseInt(nextEpisodeOfCareIdentifier);
      }
      
      for (int i = 0; i < NR_OF_EPISODESOFCARE; i++) {
        EpisodeOfCare p = createNewEpisodeOfCare(nextEocId + i);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
          String s = mapper.writeValueAsString(p);
          wr.write(s.getBytes());
        }
        connection.getResponseCode();
        connection.getResponseMessage();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createEncounters()
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/encounters");
      String nextEncounterIdentifier = getNextEncounterIdentifier();
      int nextEncId = 0;
      if (nextEncounterIdentifier != null && !"".equals(nextEncounterIdentifier)) {
        nextEncId = Integer.parseInt(nextEncounterIdentifier);
      }
      
      for (int i = 0; i < NR_OF_ENCOUNTERS; i++) {
        Encounter p = createNewEncounter(nextEncId + i);
        ObjectMapper mapper = new ObjectMapper();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
          String s = mapper.writeValueAsString(p);
          wr.write(s.getBytes());
        }
        connection.getResponseCode();
        connection.getResponseMessage();
        //        System.out.println("responseCode = " + responseCode + " Message: " + responseMessage);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private Patient createNewPatient()
  {
    Patient p = new Patient();
    p.setFirstname(firstnames.get(new Random().nextInt(firstnames.size() - 1)));
    p.setFamilyname(lastnames.get(new Random().nextInt(lastnames.size() - 1)));
    LocalDate localDate = generateRandomBirthDate();
    String identifier = "";
    if (localDate.getDayOfMonth() < 10) {
      identifier += "0" + localDate.getDayOfMonth();
    } else {
      identifier += localDate.getDayOfMonth();
    }
    if (localDate.getMonth().getValue() < 10) {
      identifier += "0" + localDate.getMonth().getValue();
    } else {
      identifier += localDate.getMonth().getValue();
    }
    identifier += ("" + localDate.getYear()).substring(2, 4);
    int i = new Random().nextInt(9999);
    if (i < 999) {
      i += 1000;
    }
    identifier += i;
    p.setIdentifier(identifier);
    String nextAddressIdentifier = getNextAddressIdentifier();
    if (nextAddressIdentifier != null && !"".equals(nextAddressIdentifier)) {
      int i1 = Integer.parseInt(nextAddressIdentifier);
      if (i1 > 0) {
        Address address = getAddress(new Random().nextInt(i1));
        if (address != null) {
          p.setAddress(address.getIdentifier());
        }
      }
    }
    return p;
  }
  
  private Address getAddress(int i1)
  {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/addresses/" + i1);
    
    try {
      HttpResponse response = HttpClientBuilder.create().build().execute(request);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        String s;
        ObjectMapper mapper;
        s = EntityUtils.toString(entity);
        mapper = new ObjectMapper();
        return mapper.readValue(s, Address.class);
      } else {
        return null;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private int getAddressCount()
  {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/addresses/amount");
    return retrieveAmount(request);
  }
  
  private int retrieveAmount(HttpUriRequest request)
  {
    HttpResponse response;
    try {
      response = HttpClientBuilder.create().build().execute(request);
      HttpEntity entity = response.getEntity();
      String s = EntityUtils.toString(entity);
      int patientCount = 0;
      try {
        patientCount = Integer.parseInt(s);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
      return patientCount;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }
  
  private int getPatientCount()
  {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/patients/amount");
    return retrieveAmount(request);
  }
  
  private Address createNewAddress(int id)
  {
    Address address = new Address();
    address.setCity(cities.get(new Random().nextInt(cities.size() - 1)));
    String street = streets.get(new Random().nextInt(streetnumbers.size() - 1));
    String line = String.format("%s %s", street, streetnumbers.get(new Random().nextInt(streetnumbers.size() - 1)));
    
    address.setLine(line);
    int postalCode = new Random().nextInt(9999);
    if (postalCode < 999) {
      postalCode += 1000;
    }
    address.setPostalCode("" + postalCode);
    address.setState(states.get(new Random().nextInt(states.size() - 1)));
    address.setIdentifier("" + id);
    
    return address;
  }
  
  private Period createNewPeriod()
  {
    Period period = new Period();
    period.setStart(generateRandomTime(LocalDateTime.now().getYear() - 20, LocalDateTime.now().getYear()));
    Random rnd = new Random();
    double roll = rnd.nextDouble();
    if (roll < 1.0) {
      LocalDateTime end = period.getStart().plusHours(rnd.nextInt(1000));
      if (end.isAfter(LocalDateTime.now())) {
        end = LocalDateTime.now().minusHours(1);
        period.setEnd(end);
      } else {
        period.setEnd(end);
      }
    }
    return period;
  }
  
  private EpisodeOfCare createNewEpisodeOfCare(int id)
  {
    EpisodeOfCare episodeOfCare = new EpisodeOfCare();
    episodeOfCare.setIdentifier("" + id);
    Period p = null;
    Random rnd = new Random();
    double roll = rnd.nextDouble();
    if (roll < 0.2) {
      // 20 percent of all episodes of care are closed..
      for (Code code : status) {
        if (code.code.equals("finished")) {
          episodeOfCare.setStatus(code.id);
          p = getPeriod(true);
        }
      }
    } else {
      episodeOfCare.setStatus(status.get(new Random().nextInt(status.size() - 1)).id);
    }
    if (p == null) {
      p = getPeriod(false);
    }
    episodeOfCare.setPeriod(p.getIdentifier().toString());
    
    episodeOfCare.setResponsibleUnit(sksCodes.get(new Random().nextInt(sksCodes.size() - 1)).id);
    
    return episodeOfCare;
  }
  
  private Encounter createNewEncounter(int id)
  {
    Encounter encounter = new Encounter();
    encounter.setIdentifier("" + id);
    encounter.setStatus(status.get(new Random().nextInt(status.size() - 1)).id);
    encounter.setResponsibleUnit(sksCodes.get(new Random().nextInt(sksCodes.size() - 1)).id);
    
    return encounter;
  }
  
  private LocalDate generateRandomBirthDate()
  {
    Random random = new Random();
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);
    
    LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
    return randomBirthDate;
  }
  
  private LocalDateTime generateRandomTime(
    int startYear,
    int endYear)
  {
    Random random = new Random();
    int minDay = (int) LocalDate.of(startYear, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(endYear, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);
    
    LocalDateTime randomDate = LocalDateTime.ofEpochSecond(randomDay * 60 * 60, 0, ZoneOffset.UTC);
    return randomDate;
  }
  
  private static void getPatients()
  {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/patients");
    
    try {
      HttpResponse response = HttpClientBuilder.create().build().execute(request);
      StatusLine statusLine = response.getStatusLine();
      System.out.println("statusLine = " + statusLine);
      HttpEntity entity = response.getEntity();
      String s = EntityUtils.toString(entity);
      System.out.println("s = " + s);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private String getNextAddressIdentifier()
  {
    return getNextEntityIdentifier("addresses");
  }
  
  private String getNextEpisodeOfCareIdentifier()
  {
    return getNextEntityIdentifier("episodesofcare");
  }
  
  private String getNextEncounterIdentifier()
  {
    return getNextEntityIdentifier("encounters");
  }
  
  private String getNextEntityIdentifier(String identifier)
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/" + identifier + "/nextIdentifier");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      
      return retriveIdentifier(connection);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "0";
  }
  
  private String retriveIdentifier(HttpURLConnection connection) throws IOException
  {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      int responseCode = connection.getResponseCode();
      String responseMessage = connection.getResponseMessage();
      
      return response.toString();
    }
  }
  
  private Period getPeriod(boolean hasEnd)
  {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/periods");
    
    try {
      HttpResponse response = HttpClientBuilder.create().build().execute(request);
      
      HttpEntity entity = response.getEntity();
      String s = EntityUtils.toString(entity);
      ObjectMapper mapper = new ObjectMapper();
      Period[] periods = mapper.readValue(s, Period[].class);
      return getRandomPeriodWithEnd(periods);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private Period getRandomPeriodWithEnd(Period[] periods)
  {
    boolean hasEnd = false;
    for (Period period : periods) {
      if (period.getEnd() != null) {
        hasEnd = true;
        break;
      }
    }
    if (!hasEnd) {
      return null;
    }
    Random rnd = new Random();
    Period period = periods[rnd.nextInt(periods.length)];
    if (period.getEnd() != null) {
      return period;
    } else {
      return getRandomPeriodWithEnd(periods);
    }
  }
  
  class JsonTime {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;
  }
  class Code
  {
    String id;
    String code;
    
    public Code(
      String id,
      String code)
    {
      
      this.id = id;
      this.code = code;
    }
  }
}
