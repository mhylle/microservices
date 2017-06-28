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
  
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Patient getPatient(@PathParam("id") String id) {
    return Repository.getInstance().getPatients().stream()
      .filter(patient -> patient.getIdentifier().equals(id))
      .findFirst().orElse(null);
  }
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String addPatient(Patient patient) {
    Repository.getInstance().addPatient(patient);
    return "Patient added";
  }
  
  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getPatients().size();
  }
}
