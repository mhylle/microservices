package info.mhylle.playground.microservices.routes;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.Patient;

@Path("/patients")
public class Patients {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Patient> getPatients() {
    return Repository.getInstance().getPatients();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String addPatient(Patient patient) {
    Repository.getInstance().addPatient(patient);
    return "Patient added";
  }
}
