package info.mhylle.playground.microservices.routes;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import info.mhylle.playground.microservices.model.Patient;

@Path("/patients")
public class Patients
{
  List<Patient> patients;
  
  public Patients()
  {
    patients = new ArrayList<>();
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Patient> getPatients(){
    return patients;
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addPatient(Patient patient) {
    patients.add(patient);
  }
  
}
