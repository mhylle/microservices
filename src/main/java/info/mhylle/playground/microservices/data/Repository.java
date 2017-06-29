package info.mhylle.playground.microservices.data;

import java.util.*;

import info.mhylle.playground.microservices.model.*;

public class Repository
{
  private static Repository _instance;
  private List<Patient> patients;
  private List<EpisodeOfCare> episodesOfCare;
  private List<Encounter> encounters;
  private List<Address> addresses;
  private List<Period> periods;
  private List<SKSCode> sksCodes;
  private List<SORCode> sorCodes;
  
  private Repository()
  {
    patients = new ArrayList<>();
    episodesOfCare = new ArrayList<>();
    encounters = new ArrayList<>();
    addresses = new ArrayList<>();
    periods = new ArrayList<>();
    sksCodes = new ArrayList<>();
    sorCodes = new ArrayList<>();
  }
  
  public static Repository getInstance()
  {
    if (_instance == null) {
      _instance = new Repository();
    }
    return _instance;
  }
  
  public void addPatient(Patient patient)
  {
    if (patients.contains(patient)) {
      return;
    }
    
    patients.add(patient);
  }
  
  public List<Patient> getPatients()
  {
    return patients;
  }
  
  public void addEpisodeOfCare(EpisodeOfCare episodeOfCare)
  {
    episodesOfCare.add(episodeOfCare);
  }
  
  public List<EpisodeOfCare> getEpisodesOfCare()
  {
    return episodesOfCare;
  }
  
  public void addEncounter(Encounter encounter)
  {
    encounters.add(encounter);
  }
  
  public List<Encounter> getEncounters()
  {
    return encounters;
  }
  
  public void addAddress(Address address)
  {
    if (!addresses.contains(address)) {
      addresses.add(address);
    }
  }
  
  public List<Address> getAddresses()
  {
    return addresses;
  }
  
  public void addPeriod(Period period)
  {
    periods.add(period);
  }
  
  public List<Period> getPeriods()
  {
    return periods;
  }
  
  public void addSksCode(SKSCode sksCode)
  {
    sksCodes.add(sksCode);
  }
  
  public List<SKSCode> getSksCodes()
  {
    return sksCodes;
  }
  
  public void addSorCode(SORCode sorCode)
  {
    sorCodes.add(sorCode);
  }
  
  public List<SORCode> getSorCodes()
  {
    return sorCodes;
  }
}
