package info.mhylle.playground.microservices;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.*;

import info.mhylle.playground.microservices.model.*;

public class ServiceClient
{
  private static final int NR_OF_PATIENTS = 50000;
  private static final int NR_OF_ADDRESSES = 50000;
  private List<String> firstnames;
  private List<String> lastnames;
  private List<String> cities;
  private List<String> states;
  private List<String> streetnumbers;
  private List<String> streets;
  
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
    getAddressCount();
    readData(firstnames, "firstnames", "firstname");
    readData(lastnames, "lastnames", "lastname");
    readData(cities, "cities", "city");
    readData(states, "states", "state");
    readData(streetnumbers, "streetnumbers", "streetnumber");
    readData(streets, "streets", "streetname");
    
    createAddresses();
    long startTime = System.nanoTime();
    createPatients();
    long endTime = System.nanoTime();
    long elapsed = endTime - startTime;
    
    System.out.println("elapsed time= " + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0);
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
  
  private void createPatients()
  {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/patients");
      
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
        int responseCode = connection.getResponseCode();
        String responseMessage = connection.getResponseMessage();
        //        System.out.println("responseCode = " + responseCode + " Message: " + responseMessage);
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
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
      System.out.println("nextAddrId = " + nextAddrId);
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
        int responseCode = connection.getResponseCode();
        String responseMessage = connection.getResponseMessage();
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
      
      Address address = getAddress(new Random().nextInt(i1));
      p.setAddress(address);
    }
    return p;
  }
  
  private Address getAddress(int i1)
  {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/addresses/" + i1);
    
    try {
      HttpResponse response = HttpClientBuilder.create().build().execute(request);
      HttpEntity entity = response.getEntity();
      String s = EntityUtils.toString(entity);
      ObjectMapper mapper = new ObjectMapper();
//      System.out.println("s = " + s);
      return mapper.readValue(s, Address.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void getAddressCount() {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/addresses");
    HttpResponse response = null;
    try {
      response = HttpClientBuilder.create().build().execute(request);
      HttpEntity entity = response.getEntity();
      String s = EntityUtils.toString(entity);

      ObjectMapper mapper = new ObjectMapper();
      Address[] addresses = mapper.readValue(s, Address[].class);
      System.out.println("addresses = " + addresses.length);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private Address createNewAddress(int id)
  {
    Address address = new Address();
    address.setCity(cities.get(new Random().nextInt(cities.size() - 1)));
    String street = streets.get(new Random().nextInt(streetnumbers.size() - 1));
    String line = new StringBuilder().append(street)
      .append(" ")
      .append(streetnumbers.get(new Random().nextInt(streetnumbers.size() - 1)))
      .toString();
    
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
  
  private LocalDate generateRandomBirthDate()
  {
    Random random = new Random();
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);
    
    LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
    return randomBirthDate;
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
    
    try {
      URL url = new URL("http://localhost:8080/microservices/api/addresses/nextIdentifier");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      
      try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        String inputLine = "";
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        int responseCode = connection.getResponseCode();
        String responseMessage = connection.getResponseMessage();
        
        return response.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "0";
  }
}
