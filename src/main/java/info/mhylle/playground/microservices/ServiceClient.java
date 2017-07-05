package info.mhylle.playground.microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.mhylle.playground.microservices.data.Code;
import info.mhylle.playground.microservices.data.Values;
import info.mhylle.playground.microservices.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServiceClient {
  private static final int NR_OF_PATIENTS = 0;
  private static final int NR_OF_ADDRESSES = 0;
  private static final int NR_OF_EPISODESOFCARE = 0;
  private static final int NR_OF_ENCOUNTERS = 0;
  private static final int NR_OF_PERIODS = 0;
  private List<String> firstnames;
  private List<String> lastnames;
  private List<String> cities;
  private List<String> states;
  private List<String> streetnumbers;
  private List<String> streets;
  private List<Code> status;
  private List<Code> sksCodes;
  //  private boolean generatePatients = true;
  private boolean generateEpisodesOfCare = true;
  private boolean generateEncounters = true;

  public static void main(String[] args) {
    ServiceClient serviceClient = new ServiceClient();
    serviceClient.start();
  }

  private void start() {
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
    readData(firstnames, "firstnames");
    readData(lastnames, "lastnames");
    readData(cities, "cities");
    readData(states, "states");
    readData(streetnumbers, "streetnumbers");
    readData(streets, "streets");
    readCode(status, "status");
    readCode(sksCodes, "skscodes");
    createPeriods();
    createAddresses();
    long startTime = System.nanoTime();
    createPatients();
    long endTime = System.nanoTime();
    long elapsed = endTime - startTime;
    System.out.println("elapsed time= " + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0);
//    createEpisodesOfCare();
//    createEncounters();
//
    startGenerators();
  }

  private void startGenerators() {
    Runnable episodeOfCareGenerator = () -> {
      int counter = 0;
      while (generateEpisodesOfCare) {
        try {
          Thread.sleep(new Random().nextInt(1000) + 100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          createEpisodeOfCare(counter++);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    Runnable encountersGenerator = () -> {
      int counter = 0;
      while (generateEncounters) {
        try {
          Thread.sleep(new Random().nextInt(1000) + 100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          createEncounter(counter++);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    Thread eocRunner = new Thread(episodeOfCareGenerator);
    Thread encRunner = new Thread(encountersGenerator);
    eocRunner.start();
    encRunner.start();
  }

  private void readData(List<String> list, String type) {

    try {
      InputStream in = new FileInputStream(".\\resources\\" + type + ".json");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder json = new StringBuilder();
      String data;
      while ((data = reader.readLine()) != null) {
        json.append(data);
      }

      ObjectMapper mapper = new ObjectMapper();
      Values[] keys = mapper.readValue(json.toString(), Values[].class);
      for (Values values : keys) {
        list.add(values.getKey());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readCode(List<Code> list, String type) {

    try {
      InputStream in = new FileInputStream(".\\resources\\" + type + ".json");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder json = new StringBuilder();
      String data;
      while ((data = reader.readLine()) != null) {
        json.append(data);
      }
      ObjectMapper mapper = new ObjectMapper();
      Code[] strings = mapper.readValue(json.toString(), Code[].class);
      Collections.addAll(list, strings);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createPatients() {
    try {

      long startTime = System.nanoTime();
      for (int i = 0; i < NR_OF_PATIENTS; i++) {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (i%1000 == 0) {
          long endTime = System.nanoTime();
          long elapsed = endTime - startTime;

          double v = TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0;
          if (i >0) {
            System.out.println(
                "Created " + i + " patients in "
                    + v + " seconds (average=" + TimeUnit.MILLISECONDS.convert((elapsed /i), TimeUnit.NANOSECONDS) / 1000.0);
          }

        }
        createPatient();
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

  private void createPatient() throws IOException {
    try {
      URL url = new URL("http://localhost:8080/microservices/api/patients");
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createPeriods() {
    try {
      long startTime = System.nanoTime();
      URL url = new URL("http://localhost:8080/microservices/api/periods");

      for (int i = 0; i < NR_OF_PERIODS; i++) {
        Period p = createNewPeriod();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
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
        InputStream errorStream = connection.getErrorStream();
        if (errorStream != null) {
          try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
            String buffer;
            StringBuilder input = new StringBuilder();
            while ((buffer = reader.readLine()) != null) {
              input.append(buffer);
            }
            System.out.println("input = " + input);
          }
        }
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

  private void createAddresses() {
    try {
      long startTime = System.nanoTime();
      URL url = new URL("http://localhost:8080/microservices/api/addresses");
      String nextAddressIdentifier = getNextAddressIdentifier();
      int nextAddrId = 0;
      //noinspection ConstantConditions
      if (nextAddressIdentifier != null && !"".equals(nextAddressIdentifier)) {
        nextAddrId = Integer.parseInt(nextAddressIdentifier);
      }

      for (int i = 0; i < NR_OF_ADDRESSES; i++) {
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        createAddress(url, nextAddrId, i);
      }
      long endTime = System.nanoTime();
      long elapsed = endTime - startTime;
      System.out.println(
          "Created " + NR_OF_ADDRESSES + " Addresses in "
              + TimeUnit.MILLISECONDS.convert(elapsed, TimeUnit.NANOSECONDS) / 1000.0 + " seconds");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createAddress(final URL url, final int nextAddrId, final int i) throws IOException {
    Runnable runnable = () -> {
      try {

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
      } catch (IOException e) {
        e.printStackTrace();
      }
    };
    Thread runner = new Thread(runnable);
    runner.start();
  }

  private void createEpisodesOfCare() {
    try {
      for (int i = 0; i < NR_OF_EPISODESOFCARE; i++) {
        createEpisodeOfCare(i);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createEpisodeOfCare(int i) throws IOException {
    URL url = new URL("http://localhost:8080/microservices/api/episodesofcare");
    String nextEpisodeOfCareIdentifier = getNextEpisodeOfCareIdentifier();
    int nextEocId = 0;
    //noinspection ConstantConditions
    if (nextEpisodeOfCareIdentifier != null && !"".equals(nextEpisodeOfCareIdentifier)) {
      nextEocId = Integer.parseInt(nextEpisodeOfCareIdentifier);
    }
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

  private void createEncounters() {
    try {
      for (int i = 0; i < NR_OF_ENCOUNTERS; i++) {
        createEncounter(i);
        //        System.out.println("responseCode = " + responseCode + " Message: " + responseMessage);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createEncounter(int i) throws IOException {
    URL url = new URL("http://localhost:8080/microservices/api/encounters");
    String nextEncounterIdentifier = getNextEncounterIdentifier();
    int nextEncId = 0;
    //noinspection ConstantConditions
    if (nextEncounterIdentifier != null && !"".equals(nextEncounterIdentifier)) {
      nextEncId = Integer.parseInt(nextEncounterIdentifier);
    }
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
  }

  private Patient createNewPatient() {
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
    //noinspection ConstantConditions
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

  private Address getAddress(int i1) {
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

  private int getAddressCount() {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/addresses/amount");
    return retrieveAmount(request);
  }

  private int retrieveAmount(HttpUriRequest request) {
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

  private int getPatientCount() {
    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/patients/amount");
    return retrieveAmount(request);
  }

  private Address createNewAddress(int id) {
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

  private Period createNewPeriod() {
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

  private EpisodeOfCare createNewEpisodeOfCare(int id) {
    EpisodeOfCare episodeOfCare = new EpisodeOfCare();
    episodeOfCare.setIdentifier("" + id);
    Period p = null;
    Random rnd = new Random();
    double roll = rnd.nextDouble();
    if (roll < 0.2) {
      // 20 percent of all episodes of care are closed..
      for (Code code : status) {
        if (code.getCode().equals("finished")) {
          episodeOfCare.setStatus(code.getId());
          p = getPeriod();
        }
      }
    } else {
      episodeOfCare.setStatus(status.get(new Random().nextInt(status.size() - 1)).getId());
    }
    if (p == null) {
      p = getPeriod();
    }
    episodeOfCare.setPeriod(p != null ? p.getIdentifier().toString() : null);
    episodeOfCare.setResponsibleUnit(sksCodes.get(new Random().nextInt(sksCodes.size() - 1)).getId());

    return episodeOfCare;
  }

  private Encounter createNewEncounter(int id) {
    Encounter encounter = new Encounter();
    encounter.setIdentifier("" + id);
    encounter.setStatus(status.get(new Random().nextInt(status.size() - 1)).getId());
    encounter.setResponsibleUnit(sksCodes.get(new Random().nextInt(sksCodes.size() - 1)).getId());

    return encounter;
  }

  private LocalDate generateRandomBirthDate() {
    Random random = new Random();
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);

    return LocalDate.ofEpochDay(randomDay);
  }

  private LocalDateTime generateRandomTime(
      int startYear,
      int endYear) {
    Random random = new Random();
    int minDay = (int) LocalDate.of(startYear, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(endYear, 1, 1).toEpochDay();
    long randomDay = minDay + random.nextInt(maxDay - minDay);

    return LocalDateTime.ofEpochSecond(randomDay * 60 * 60, 0, ZoneOffset.UTC);
  }

//  private static void getPatients() {
//    HttpUriRequest request = new HttpGet("http://localhost:8080/microservices/api/patients");
//
//    try {
//      HttpResponse response = HttpClientBuilder.create().build().execute(request);
//      StatusLine statusLine = response.getStatusLine();
//      System.out.println("statusLine = " + statusLine);
//      HttpEntity entity = response.getEntity();
//      String s = EntityUtils.toString(entity);
//      System.out.println("s = " + s);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  private String getNextAddressIdentifier() {
    return getNextEntityIdentifier("addresses");
  }

  private String getNextEpisodeOfCareIdentifier() {
    return getNextEntityIdentifier("episodesofcare");
  }

  private String getNextEncounterIdentifier() {
    return getNextEntityIdentifier("encounters");
  }

  private String getNextEntityIdentifier(String identifier) {
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

  private String retriveIdentifier(HttpURLConnection connection) throws IOException {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      connection.getResponseCode();
      connection.getResponseMessage();

      return response.toString();
    }
  }

  private Period getPeriod() {
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

  private Period getRandomPeriodWithEnd(Period[] periods) {
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
}
