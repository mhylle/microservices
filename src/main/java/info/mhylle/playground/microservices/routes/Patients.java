package info.mhylle.playground.microservices.routes;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.Patient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patients")
public class Patients {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Patient> getPatients() {
    return Repository.getInstance().getPatients();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addPatient(Patient patient) {
    Repository.getInstance().addPatient(patient);
  }
}
